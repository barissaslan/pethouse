def to = emailextrecipients([
          [$class: 'CulpritsRecipientProvider'],
          [$class: 'DevelopersRecipientProvider'],
          [$class: 'RequesterRecipientProvider']
  ])

def content = '${JELLY_SCRIPT,template="html"}'

pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew assemble'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh './gradlew docker'
            }
        }

        stage('Push Docker Image') {
            environment {
                DOCKER_HUB_LOGIN = credentials('dockerhub.barisaslan')
            }
            steps {
                sh 'docker login --username=$DOCKER_HUB_LOGIN_USR --password=$DOCKER_HUB_LOGIN_PSW'
                sh './gradlew dockerPush'
            }
        }

        stage('Deploy Docker Image') {
            steps {
                sh 'docker-compose pull'
                sh 'docker-compose up -d --no-build'
            }
        }

        stage('Sonar Scanner') {
            steps {
                script {
                    def sonarqubeScannerHome = tool name: 'sonar', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
                    withCredentials([string(credentialsId: 'sonarqube.admin', variable: 'sonarLogin')]) {
                        sh '${sonarqubeScannerHome}/bin/sonar-scanner -e -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=${sonarLogin} -Dsonar.projectName=pethouse -Dsonar.projectVersion=${env.BUILD_NUMBER} -Dsonar.projectKey=PH -Dsonar.sources=src/main/ -Dsonar.tests=src/test/ -Dsonar.language=java -Dsonar.java.binaries=.'
                    }
                }
            }
        }
    }

    post {
        failure {
            echo 'Sending email...'
            emailext(body: content,
                mimeType: 'text/html',
                replyTo: '$DEFAULT_REPLYTO',
                subject: "${env.JOB_NAME} - Build #${env.BUILD_NUMBER} ${currentBuild.result}",
                to: to,
                attachLog: true )
        }
    }
}
