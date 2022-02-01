pipeline {
    agent {
        label 'docker-build-node'
    }

    enviroment {
        DOCKERHUB_CREDENTIALS = credentials('f1bc304d-fb89-40a2-bf67-8f2e1c3bf542')
        IMAGE_NAME = 'mojefajne/psi'
        IMAGE_TAG = 'latest'
    }
    
    stages {
        stage('AuthDockerHub') {
            steps {
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
            }
        }
        
        stage('Compile') {
            steps {
                sh './mvnw clean package'
            }
        }
        
        stage('Build') {
            steps {
                sh 'docker build --no-cache -t $IMAGE_NAME:$IMAGE_TAG backend/'
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
