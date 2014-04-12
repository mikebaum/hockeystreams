package org.mbaum.serviio.model;

import java.util.List;

import org.mbaum.common.model.Model;

import static org.mbaum.common.model.Model.IdBuilder.createId;

public interface OnlineRepositoryModel extends Model<OnlineRepositoryModel>
{
	static final ModelValueId<OnlineRepositoryModel, Integer> ID = createId();
	static final ModelValueId<OnlineRepositoryModel, String> REPOSITORY_TYPE = createId();
	static final ModelValueId<OnlineRepositoryModel, String> CONTENT_URL = createId();
	static final ModelValueId<OnlineRepositoryModel, String> FILE_TYPE = createId();
	static final ModelValueId<OnlineRepositoryModel, String> REPOSITORY_NAME = createId();
	static final ModelValueId<OnlineRepositoryModel, Boolean> ENABLED = createId();
	static final ModelValueId<OnlineRepositoryModel, List<Integer>> ACCESS_GROUP_IDS = createId();
}