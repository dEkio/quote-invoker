#!groovy
pipeline {

    agent any

    environment {
        BRANCH_NAME=env.GIT_BRANCH.replace("origin/", "")
    }

    stages {
        stage('Build') {
            steps {
                    bat 'mvn clean verify'
            }
        }
        stage('Publish Pacts') {
            steps {
                    bat 'mvn pact:publish -Dpact.consumer.version=${GIT_COMMIT} -Dpact.tag=${BRANCH_NAME}'
            }
        }
        stage('Check Pact Verifications') {
            steps {
                bat "pact-broker can-i-deploy --retry-while-unknown=12 --retry-interval=10 -a quote-invoker -b http://localhost -e ${GIT_COMMIT}"
            }
        }
        stage('Deploy') {
            when {
                branch 'master'
            }
            steps {
                echo 'Deploying to prod now...'
            }
        }
    }

}