package org.mbaum.serviio.model;

import static org.mbaum.common.model.ModelValueIdUtils.createId;

import java.util.List;

import org.mbaum.common.model.ModelSpec;
import org.mbaum.common.model.ModelValueId;

import com.google.common.collect.Lists;

public class SharedFolderModel implements ModelSpec
{
    public static final ModelValueId<SharedFolderModel, Integer> ID = createId( "id", 0 );
    public static final ModelValueId<SharedFolderModel, String> FOLDER_PATH = createId( "folderPath", "" );
    public static final ModelValueId<SharedFolderModel, List<String>> SUPPORTED_FILE_TYPES = createId( "supportFileTypes", (List<String>) Lists.<String>newArrayList() );
    public static final ModelValueId<SharedFolderModel, Boolean> DESCRIPTIVE_METADATA_SUPPORTED = createId( "descriptiveMetadataSupported", false );
    public static final ModelValueId<SharedFolderModel, Boolean> SCAN_FOR_UPDATES = createId( "scanForUpdates", false );
    public static final ModelValueId<SharedFolderModel, List<Integer>> ACCESS_GROUP_IDS = createId( "accessGroupIds", (List<Integer>) Lists.<Integer>newArrayList() );
    
    private SharedFolderModel() {}
}
