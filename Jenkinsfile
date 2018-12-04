#!groovy
pipeline {

    agent any

    parameters {
        string(name: 'pactConsumerTags', defaultValue: 'master')
        string(name: 'pactVersion', defaultValue: '1')
    }

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
                    bat 'mvn pact:publish -Dpact.consumer.version=%pactVersion% -Dpact.consumer.tag=%pactConsumerTags%'
            }
        }
        stage('Check Pact Verifications') {
            steps {
                dir('pact/bin') {
                    bat ".\\pact-broker can-i-deploy --retry-while-unknown=12 --retry-interval=20 -a quote-invoker -b http://localhost -e %pactVersion%"
                }
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