package org.mbaum.serviio.model;

import static org.mbaum.common.model.ModelValueIdUtils.createId;

import java.util.List;

import org.mbaum.common.model.Model;
import org.mbaum.common.model.ModelValueId;

import com.google.common.collect.Lists;

public interface OnlineRepositoryModel extends Model<OnlineRepositoryModel>
{
	static final ModelValueId<OnlineRepositoryModel, Integer> ID = createId( "id", 0 );
	static final ModelValueId<OnlineRepositoryModel, String> REPOSITORY_TYPE = createId( "repositoryType", "" );
	static final ModelValueId<OnlineRepositoryModel, String> CONTENT_URL = createId( "contentUrl", "" );
	static final ModelValueId<OnlineRepositoryModel, String> FILE_TYPE = createId( "fileType", "" );
	static final ModelValueId<OnlineRepositoryModel, String> REPOSITORY_NAME = createId( "repositoryName", "" );
	static final ModelValueId<OnlineRepositoryModel, Boolean> ENABLED = createId( "enabled", false );
	static final ModelValueId<OnlineRepositoryModel, List<Integer>> ACCESS_GROUP_IDS = createId( "accessGroupIds", (List<Integer>) Lists.<Integer>newArrayList() );
}