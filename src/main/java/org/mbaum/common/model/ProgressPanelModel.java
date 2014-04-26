package org.mbaum.common.model;

import static org.mbaum.common.model.ModelValueIdUtils.createId;

public class ProgressPanelModel implements ModelSpec
{
    private static boolean DEFAULT_INDETERMINANT = false;
    private static String DEFAULT_MESSAGE = "";
    
    public static final ModelValueId<ProgressPanelModel, String> MESSAGE = createId( "Message", DEFAULT_MESSAGE );
    public static final ModelValueId<ProgressPanelModel, Boolean> INDETERMINATE = createId( "Indeterminate", DEFAULT_INDETERMINANT );
	
	private ProgressPanelModel() {}
}