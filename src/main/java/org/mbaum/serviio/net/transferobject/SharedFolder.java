package org.mbaum.serviio.net.transferobject;

import java.util.List;

public class SharedFolder
{
	private int id;
	private String folderPath;
	private List<String> supportedFileTypes;
	private boolean descriptiveMetadataSupported;
	private boolean scanForUpdates;
	private List<Integer> accessGroupIds;

	public int getId()
	{
		return id;
	}

	public void setId( int id )
	{
		this.id = id;
	}

	public String getFolderPath()
	{
		return folderPath;
	}

	public void setFolderPath( String folderPath )
	{
		this.folderPath = folderPath;
	}

	public List<String> getSupportedFileTypes()
	{
		return supportedFileTypes;
	}

	public void setSupportedFileTypes( List<String> supportedFileTypes )
	{
		this.supportedFileTypes = supportedFileTypes;
	}

	public boolean isDescriptiveMetadataSupported()
	{
		return descriptiveMetadataSupported;
	}

	public void setDescriptiveMetadataSupported(
			boolean descriptiveMetadataSupported )
	{
		this.descriptiveMetadataSupported = descriptiveMetadataSupported;
	}

	public boolean isScanForUpdates()
	{
		return scanForUpdates;
	}

	public void setScanForUpdates( boolean scanForUpdates )
	{
		this.scanForUpdates = scanForUpdates;
	}

	public List<Integer> getAccessGroupIds()
	{
		return accessGroupIds;
	}

	public void setAccessGroupIds( List<Integer> accessGroupIds )
	{
		this.accessGroupIds = accessGroupIds;
	}

	@Override
	public String toString()
	{
		return "SharedFolder [id=" + id + ", folderPath=" + folderPath
				+ ", supportedFileTypes=" + supportedFileTypes
				+ ", descriptiveMetadataSupported="
				+ descriptiveMetadataSupported + ", scanForUpdates="
				+ scanForUpdates + ", accessGroupIds=" + accessGroupIds + "]";
	}
}
