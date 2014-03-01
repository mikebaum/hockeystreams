package org.mbaum.serviio.net.transferobject;

import java.util.List;

import com.google.common.collect.Lists;

public class ServiioAction
{
	private String name;
	private List<String> parameter;
	
	public ServiioAction(){}
	
	public ServiioAction( String name, List<String> parameter )
    {
	    this.name = name;
	    this.parameter = parameter;
    }
	
	public ServiioAction( String name )
	{
		this( name, createEmptyList() );
	}
	
	public String getName()
	{
		return name;
	}
	public void setName( String name )
	{
		this.name = name;
	}
	public List<String> getParameter()
	{
		return parameter;
	}
	public void setParameter( List<String> parameter )
	{
		this.parameter = parameter;
	}
	
	private static List<String> createEmptyList()
	{
		return Lists.newArrayList();
	}
}
