pipeline {
    agent {
        label 'docker-build-node'
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('aa5d6d7c-2b40-417c-996b-fc08ca7366bd')
        IMAGE_NAME = 'dompazdzierz/psi'
        IMAGE_TAG = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
    }
    
    stages {
        stage('AuthDockerHub') {
            steps {
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
            }
        }
        
        stage('Build') {
            steps {
                sh 'docker build --no-cache -t $IMAGE_NAME:$IMAGE_TAG .'
            }
        }
        
         stage('PublishImage') {
            steps {
                sh 'docker push $IMAGE_NAME:$IMAGE_TAG'
            }
        }
    }
    
    post {
        always {
            sh 'docker logout'
        }
    }
}
