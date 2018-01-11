/*
  The purpose of this job is to define a Jenkins pipeline for build, tests and deployment of the library.
*/

def libraryGitUrl = "${CARTRIDGE_CLONE_URL}"
def libraryGitCredentials = "adop-jenkins-master"

// Folders
def projectFolderName = "${PROJECT_NAME}"

// Jobs
def buildLibrary = freeStyleJob(projectFolderName + "/ADOP_DSL_Cartridge_Custom_Properties_Library_Build")
def setupLibrary = freeStyleJob(projectFolderName + "/ADOP_DSL_Cartridge_Custom_Properties_Library_Deploy")

// Views
def pipelineView = buildPipelineView(projectFolderName + "/ADOP_DSL_Cartridge_Custom_Properties_Pipeline")

pipelineView.with {
    title('ADOP DSL Cartridge_Custom_Properties Library Pipeline')
    displayedBuilds(5)
    selectedJob(projectFolderName + "/ADOP_DSL_Cartridge_Custom_Properties_Library_Build")
    showPipelineParameters()
    showPipelineDefinitionHeader()
    refreshFrequency(5)
}

buildLibrary.with{
	description('This job builds and tests the ADOP DSL Cartridge Custom properties library.')
    environmentVariables {
        keepBuildVariables(true)
        keepSystemVariables(true)
    }
	properties {
		copyArtifactPermissionProperty {
			projectNames('ADOP_DSL_Cartridge_Custom_Properties_Deploy')
		}
	}
	label('docker')
    wrappers {
        preBuildCleanup()
    }
    scm {
        git {
            remote {
                name("origin")
                url(libraryGitUrl)
				credentials(libraryGitCredentials)
            }
            branch("*/master")
        }
    }
	steps {
		shell('''
chmod +x gradlew
./gradlew clean test -debug
		''')
	}
	publishers {
		archiveArtifacts("**/*")
			downstreamParameterized {
				trigger(projectFolderName + "/ADOP_DSL_Cartridge_Custom_Properties_Deploy") {
					condition("UNSTABLE_OR_BETTER")
					parameters {
						predefinedProp("B", '${BUILD_NUMBER}')
						predefinedProp("PARENT_BUILD", '${JOB_NAME}')
                }
            }
        }
	}
}

setupLibrary.with{
	description('This job deploys the ADOP DSL Cartridge_Custom_Properties library into the Jenkins library additional classpath location.')
	parameters {
        stringParam("B", '', "Parent build number.")
        stringParam("PARENT_BUILD", "ADOP_DSL_Common_Library_Build", "Parent build name.")
    }
	label('docker')
    environmentVariables {
        keepBuildVariables(true)
        keepSystemVariables(true)
    }
    wrappers {
        preBuildCleanup()
    }
	steps {
		copyArtifacts('${PARENT_BUILD}') {
            buildSelector {
                buildNumber('${B}')
            }
        }
		shell('''
rm -rf ./adop
mv src/main/groovy/* .
		''')
	}
	publishers {
			copyToMasterNotifier {
				includes('adop/**/*')
				excludes('')
				runAfterResultFinalised(false)
				overrideDestinationFolder(true)
				destinationFolder('/var/jenkins_home/userContent/job_dsl_additional_classpath/')
		}
	}
}

queue(buildLibrary)