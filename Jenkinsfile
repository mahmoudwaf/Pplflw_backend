    pipeline {
        agent any
        stages {
            stage('Build Application') {
                steps {
                    sh 'mvn -f pom.xml clean package'
                }
                post {
                    success {
                        echo "Now Archiving the Artifacts...."
                        archiveArtifacts artifacts: '**/*.jar'
                    }
                }
            }
     
            stage('Create  Docker Image'){
                steps {
                    ssh -t  "sudo docker build  . -t pplflw_backend_service:${env.BUILD_ID} "
                }
            }
     
        }
    }