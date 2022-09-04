standardPipeline {
    productPath = "sample-service-ying"
    queryName = "sample-service-ying"
    podName = "sample-service-ying"
    mvnBuilds = ["sample-09032022":"clean package test",  "test":"clean package test" ]
    dockerBuildPush = ["sample-09032022": "sample-09032022"  , "test": "test"   , "nfs": "nfs"   ,"nginx": "nginx" ]
    productionReady = false
    markAsDefault = true
    dockerBaseImageVersion = "290-11-g64f1e9f"
    productionApprovers = "i835832,i857925,i304306"
    appUid = "01gc254e4n55xgvw8m2pvp4r0y"
    secrets = [ "spring.datasource.password":"roomba123" ]
    useGitVersioning = true
    regions = ["US","EU","CN","RU","UAE","KSA", "LAB"]
    slackChannel = 'Create a new channel in slack for this and give the name here'
    slackHookUrl = 'Refer https://sap-ariba.slack.com/archives/C976XHZPV/p1520442528000173 to get this'
}
