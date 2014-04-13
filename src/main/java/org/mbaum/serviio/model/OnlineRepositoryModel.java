package org.mbaum.serviio.model;

import java.util.List;

import org.mbaum.common.model.Model;

import com.google.common.collect.Lists;

import static org.mbaum.common.model.Model.IdBuilder.createId;

public interface OnlineRepositoryModel extends Model<OnlineRepositoryModel>
{
	static final ModelValueId<OnlineRepositoryModel, Integer> ID = createId( "Id", 0 );
	static final ModelValueId<OnlineRepositoryModel, String> REPOSITORY_TYPE = createId( "Repository Type", "" );
	static final ModelValueId<OnlineRepositoryModel, String> CONTENT_URL = createId( "Url", "" );
	static final ModelValueId<OnlineRepositoryModel, String> FILE_TYPE = createId( "File Type", "" );
	static final ModelValueId<OnlineRepositoryModel, String> REPOSITORY_NAME = createId( "Name", "" );
	static final ModelValueId<OnlineRepositoryModel, Boolean> ENABLED = createId( "Enabled", false );
	static final ModelValueId<OnlineRepositoryModel, List<Integer>> ACCESS_GROUP_IDS = createId( "Access Group Ids", (List<Integer>) Lists.<Integer>newArrayList() );
}