package org.mbaum.serviio.model;

import static org.mbaum.common.model.ModelValueIdUtils.createId;

import java.util.List;

import org.mbaum.common.model.ModelSpec;
import org.mbaum.common.model.ModelValueId;

import com.google.common.collect.Lists;

public class OnlineRepositoryModel implements ModelSpec
{
    public static final ModelValueId<OnlineRepositoryModel, Integer> ID = createId( "id", 0 );
    public static final ModelValueId<OnlineRepositoryModel, String> REPOSITORY_TYPE = createId( "repositoryType", "" );
    public static final ModelValueId<OnlineRepositoryModel, String> CONTENT_URL = createId( "contentUrl", "" );
    public static final ModelValueId<OnlineRepositoryModel, String> FILE_TYPE = createId( "fileType", "" );
    public static final ModelValueId<OnlineRepositoryModel, String> REPOSITORY_NAME = createId( "repositoryName", "" );
    public static final ModelValueId<OnlineRepositoryModel, Boolean> ENABLED = createId( "enabled", false );
    public static final ModelValueId<OnlineRepositoryModel, List<Integer>> ACCESS_GROUP_IDS = createId( "accessGroupIds", (List<Integer>) Lists.<Integer>newArrayList() );
    
    private OnlineRepositoryModel() {}
}