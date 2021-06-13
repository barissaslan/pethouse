node {
    def image = docker.image('maven:3.8-jdk-11')
    image.pull()
    stage('Prep') {
        checkout scm
    }

    stage('Test') {
        image.inside("-v ${env.HOME}/.gradle:/home/gradle/.gradle") {
            sh 'cd complete && gradle test'
        }
    }
}