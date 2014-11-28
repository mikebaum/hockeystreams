package org.mbaum.serviio.net;

import static org.mbaum.common.net.parse.ResponseParsers.newJsonParser;
import static org.mbaum.common.net.parse.ResponseParsers.newMutableModelJsonParser;
import static org.mbaum.serviio.model.ServiioModel.HOST_NAME;
import static org.mbaum.serviio.model.ServiioModel.PORT;
import static org.mbaum.serviio.model.ServiioModel.REPOSITORY_RESPONSE;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.mbaum.common.execution.Process;
import org.mbaum.common.execution.ProcessContext;
import org.mbaum.common.model.Model;
import org.mbaum.common.model.MutableModel;
import org.mbaum.common.net.HttpProcess;
import org.mbaum.serviio.model.OnlineRepositoryModel;
import org.mbaum.serviio.model.RepositoryModel;
import org.mbaum.serviio.model.ServiioModel;
import org.mbaum.serviio.net.transferobject.PingResponse;

public final class ServiioApiActions
{
    private static final Logger LOGGER = Logger.getLogger( ServiioApiActions.class );

    private static final String PING_PATH = "/rest/ping";
    private static final String REPOSITORY_PATH = "/rest/repository";
    private static final String ACTION_PATH = "/rest/action";

    public static final Process<PingContext, PingResponse> PING_PROCESS = createPingProcess();
    public static final Process<RepositoryContext, MutableModel<RepositoryModel>> REPOSITORY_PROCESS =
        createRepositoryProcess();
    public static final Process<RepositoryContext, PingResponse> UPDATE_REPOSITORY_PROCESS =
        createUpdateRepositoryProcess();
    public static final Process<ActionContext, PingResponse> ACTION_PROCESS = createActionProcess();

    private ServiioApiActions()
    {
    }

    private static Process<ActionContext, PingResponse> createActionProcess()
    {
        return new HttpProcess<ActionContext, PingResponse>( newJsonParser( PingResponse.class ),
                                                             "action" )
        {
            @Override
            protected HttpUriRequest buildRequest( ActionContext context ) throws Exception
            {
                HttpEntity input = createJsonInputString( context.getAction() );

                return RequestBuilder.post()
                                     .setUri( buildUri( context.getServiioModel(), ACTION_PATH ) )
                                     .addHeader( "Accept", "application/json" )
                                     .addHeader( "Content-Type", "application/json" )
                                     .setEntity( input )
                                     .build();
            }
        };
    }

    private static Process<PingContext, PingResponse> createPingProcess()
    {
        return new HttpProcess<PingContext, PingResponse>( newJsonParser( PingResponse.class ),
                                                           "ping" )
        {
            @Override
            protected HttpUriRequest buildRequest( PingContext context ) throws NumberFormatException,
                                                                        URISyntaxException
            {
                return buildGetRequest( context.getServiioModel(), PING_PATH );
            }
        };
    }

    private static Process<RepositoryContext, MutableModel<RepositoryModel>> createRepositoryProcess()
    {
        return new HttpProcess<RepositoryContext, MutableModel<RepositoryModel>>( newMutableModelJsonParser( RepositoryModel.class ),
                                                                                  "repository" )
        {
            @Override
            protected HttpUriRequest buildRequest( RepositoryContext context ) throws Exception
            {
                return buildGetRequest( context.getServiioModel(), REPOSITORY_PATH );
            }
        };
    }

    private static Process<RepositoryContext, PingResponse> createUpdateRepositoryProcess()
    {
        return new HttpProcess<RepositoryContext, PingResponse>( newJsonParser( PingResponse.class ),
                                                                 "repository" )
        {
            @Override
            protected HttpUriRequest buildRequest( RepositoryContext context ) throws Exception
            {
                MutableModel<RepositoryModel> repositoryResponse =
                    context.getServiioModel().getValue( REPOSITORY_RESPONSE );

                List<MutableModel<OnlineRepositoryModel>> onlineRepositories =
                    repositoryResponse.getValue( RepositoryModel.ONLINE_REPOSITORIES );
                onlineRepositories.remove( 0 );
                // OnlineRepository newRepo = (OnlineRepository) onlineRepositories.get( 0
                // ).clone();
                // newRepo.setId( null );
                // newRepo.setRepositoryName( "test" );
                // newRepo.setContentUrl(
                // "http://hscontent.com/east13/OTHER_HSTV_CHIHD.m3u8?token=c8ncvLqnxOIDq7I8WafBaKi3dK6FBsBeXBhl/9vZvYdgCKnwgHjT+9JUBVbtjr74slnQCbdstYdoNBtRcMEu"
                // );
                // newRepo.setRepositoryType( "LIVE_STREAM" );
                // onlineRepositories.add( newRepo );

                return buildPutRequest( context.getServiioModel(),
                                        REPOSITORY_PATH,
                                        repositoryResponse );
            }
        };
    }

    public static interface PingContext extends ProcessContext
    {
        Model<ServiioModel> getServiioModel();
    }

    public static interface RepositoryContext extends ProcessContext
    {
        Model<ServiioModel> getServiioModel();
    }

    private static HttpUriRequest buildGetRequest( Model<ServiioModel> serviioModel, String path ) throws URISyntaxException
    {
        return RequestBuilder.get()
                             .setUri( buildUri( serviioModel, path ) )
                             .addHeader( "Accept", "application/json" )
                             .build();
    }

    private static <T> HttpUriRequest buildPutRequest( Model<ServiioModel> serviioModel,
                                                       String path,
                                                       T data ) throws URISyntaxException,
                                                               JsonGenerationException,
                                                               JsonMappingException,
                                                               IOException,
                                                               JAXBException
    {
        HttpEntity input = createJsonInputString( data );

        return RequestBuilder.put()
                             .setUri( buildUri( serviioModel, path ) )
                             .addHeader( "Accept", "application/json" )
                             .addHeader( "Content-Type", "application/json" )
                             .setEntity( input )
                             .build();
    }

    private static <T> HttpEntity createJsonInputString( T data ) throws IOException,
                                                                 JsonGenerationException,
                                                                 JsonMappingException,
                                                                 UnsupportedEncodingException
    {
        String json = new ObjectMapper().writeValueAsString( data );

        LOGGER.info( "json: " + json );

        StringEntity input = new StringEntity( json );
        input.setContentType( "application/json" );
        return input;
    }

    private static URI buildUri( Model<ServiioModel> serviioModel, String path ) throws URISyntaxException
    {
        return new URIBuilder().setHost( serviioModel.getValue( HOST_NAME ) )
                               .setPort( Integer.parseInt( serviioModel.getValue( PORT ) ) )
                               .setScheme( "http" )
                               .setPath( path )
                               .build();
    }
}
