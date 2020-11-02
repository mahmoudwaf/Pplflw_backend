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
                    sh "docker build ./Pplflw_backend -t Pplflw_backend:${env.BUILD_ID}"
                }
            }
     
        }
    }