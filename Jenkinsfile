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

        stage('Build Docker image') {
            steps {
                sh './gradlew docker'
            }
        }

        stage('Push Docker image') {
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
                sh 'docker --context barisaslan compose up -d'
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
