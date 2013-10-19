package org.mbaum.hockeystreams;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.StringUtils;

public class ConfigrationFrame
{
	private final ExceptionPanelModel mExceptionPanelModel;
	
	public ConfigrationFrame( ExceptionPanelModel model )
	{
		mExceptionPanelModel = model;
	}

	private void initGui()
	{
		JFrame frame = new JFrame( "HockeyStreams RSS Service" );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		AddIpExceptionPanel ipAcceptionPanel = new AddIpExceptionPanel( mExceptionPanelModel );
		
		JComponent ipAcceptionComponent = ipAcceptionPanel.getComponent();
		frame.setContentPane( ipAcceptionComponent );
		frame.setMinimumSize( new Dimension( ipAcceptionComponent.getPreferredSize().width, 60 ) );
		frame.pack();
		
		frame.setVisible( true );
	}
	
	public static void main( String[] args )
	{
		ExceptionPanelModel exceptionPanelModel = new ExceptionPanelModel();
		
		final ConfigrationFrame configrationFrame = new ConfigrationFrame( exceptionPanelModel );
		
		SwingUtilities.invokeLater( new Runnable()
		{
			@Override
			public void run()
			{
				configrationFrame.initGui();
			}
		} );
	}
	
	private static class AddIpExceptionPanel
	{
		private final JComponent mPanel;
		private final ExceptionPanelModel mModel;
		private final JTextField mUsernameField;
		private final JPasswordField mPasswordField;
		private final Action mAuthenticateAction;
		
		public AddIpExceptionPanel( ExceptionPanelModel model )
		{
			mUsernameField = new JTextField( 10 );
			mPasswordField = new JPasswordField( 10 );
			mAuthenticateAction = createAuthenticateAction( model );
			mModel = model;
			mPanel = createPanel( mUsernameField, mPasswordField, mAuthenticateAction, mModel );
		}
		
		private Action createAuthenticateAction( final ExceptionPanelModel model )
		{
			@SuppressWarnings("serial")
			final AbstractAction action = new AbstractAction( "Ok" )
			{
				@Override
				public void actionPerformed( ActionEvent e )
				{
					System.out.println( "I'm Authenticating." );
					
					String sessionToken = "";
					try
					{
						sessionToken = HttpUtils.loginWithHttpClient( model.getUsername(), 
																model.getPassword(), 
									                            model.getApiKey() );
					}
					catch ( Exception exception )
					{
						exception.printStackTrace();
					}
					finally
					{
						System.out.println( "Session token: " + sessionToken );
					}
					
				}
			};
			
			model.setListener( new ModelListener<ExceptionPanelModel>()
			{
				@Override
				public void modelChanged( ExceptionPanelModel model )
				{
					action.setEnabled( model.canAuthenticate() );
				}
			} );
			
			return action;
		}

		public JComponent getComponent()
		{
			return mPanel;
		}

		private static JComponent createPanel( JTextField usernameField, 
											   JPasswordField passwordField, 
											   Action authenticateAction,
											   ExceptionPanelModel model )
		{
			JPanel panel = new JPanel( new BorderLayout() );
			
			JPanel authenticationPanel = new JPanel();
			
			authenticationPanel.add( new JLabel( "username: " ) );
			usernameField.setText( model.getUsername() );
			authenticationPanel.add( usernameField );
			usernameField.addKeyListener( createUsernameFieldKeyListener( model, usernameField ) );
			
			authenticationPanel.add( new JLabel( "password: " ) );
			passwordField.setText( model.getPassword() );
		    authenticationPanel.add( passwordField );
		    passwordField.addKeyListener( createPasswordFieldKeyListener( model, passwordField ) );
		    
		    authenticateAction.setEnabled( model.canAuthenticate() );
		    authenticationPanel.add( new JButton( authenticateAction ) );
		    
		    JPanel topPanel = new JPanel( new BorderLayout() );
			topPanel.add( authenticationPanel, BorderLayout.WEST );
			
			panel.add( topPanel, BorderLayout.NORTH );
			
			return panel;
		}

		private static KeyListener createPasswordFieldKeyListener( final ExceptionPanelModel model, 
																   final JPasswordField passwordField )
		{
			return new KeyAdapter()
			{
				@Override
				public void keyReleased( KeyEvent e )
				{
					model.setPassword( new String ( passwordField.getPassword() ) );
				}
			};
		}

		private static KeyListener createUsernameFieldKeyListener( final ExceptionPanelModel model, 
																   final JTextField usernameTextField )
		{
			return new KeyAdapter()
			{
				@Override
				public void keyReleased( KeyEvent e )
				{
					model.setUsername( usernameTextField.getText() );
				}
			};
		}
	}
	
	private static class ExceptionPanelModel
	{
		private String mUsername = "mbaum";
		private String mPassword = "volleyball1";
		private final String mApiKey = "1dd7bceb51c69ba4190a5be6d59ee41e";
		private ModelListener<ExceptionPanelModel> mListener = createDoNothingListener();
		
		public String getUsername()
		{
			return mUsername;
		}
		
		public boolean canAuthenticate()
		{
			return ! StringUtils.isBlank( getUsername() ) &&
					! StringUtils.isBlank( getPassword() );
		}

		public void setListener( ModelListener<ExceptionPanelModel> listener )
		{
			mListener = listener;			
		}

		void setUsername( String username )
		{
			if ( StringUtils.equals( username, mUsername ) )
				return;
			
			mUsername = username;
			notifyObservers();
		}
		
		private void notifyObservers()
		{
			mListener.modelChanged( this );
		}

		public String getPassword()
		{
			return mPassword;
		}
		
		void setPassword( String password )
		{
			if ( StringUtils.equals( password, mPassword ) )
				return;
			
			mPassword = password;
			notifyObservers();
		}
		
		public String getApiKey()
		{
			return mApiKey;
		}

		@Override
		public String toString()
		{
			return "ExceptionPanelModel [mUsername=" + mUsername
					+ ", mPassword=" + mPassword + "]";
		}		
	}
	
	public static interface ModelListener<M>
	{
		void modelChanged( M model );
	}
	
	public static <M> ModelListener<M> createDoNothingListener()
	{
		return new ModelListener<M>()
		{
			@Override
			public void modelChanged( M model ) {}
		};
	}
}
