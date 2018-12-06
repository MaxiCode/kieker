#!/usr/bin/env groovy

pipeline {

  environment {
    DOCKER_IMAGE = 'kieker/kieker-build:openjdk8'
    DOCKER_ARGS = '--rm -u `id -u`'
  }

  agent {
    docker {
      image 'kieker/kieker-build:openjdk8'
      //image env.DOCKER_IMAGE
      args env.DOCKER_ARGS
      label 'kieker-slave-docker'
    }
  }

  //triggers {
  //  cron{}
  //}

  stages {
    stage('Precheck') {
      when {
        expression {
          (env.CHANGE_TARGET != null) && (env.CHANGE_TARGET == 'stable')
        }
      }
      steps {
        echo "BRANCH_NAME: $env.BRANCH_NAME"
        echo "CHANGE_TARGET: $env.CHANGE_TARGET"
        echo "NODE_NAME: $env.NODE_NAME"
        echo "NODE_LABELS: $env.NODE_LABELS"
        error "It is not allowed to create pull requests towards the 'stable' branch. Create a new pull request towards the 'master' branch please."
      }
    }

    stage('Compile') {
      steps {
        dir(env.WORKSPACE) {
          sh './gradlew compileJava'
          sh './gradlew compileTestJava'
        }
      }
    }


    stage('Unit Test') {
      steps {
        dir(env.WORKSPACE) {
          sh './gradlew test'
        }
      }
    }

    stage('Static Analysis') {
      steps {
        dir(env.WORKSPACE) {
          sh './gradlew check'
        }
      }
    }

    stage('Release Check Short') {
      steps {
        dir(env.WORKSPACE) {
          sh './gradlew checkReleaseArchivesShort'
          archiveArtifacts artifacts: 'build/distributions/*,kieker-documentation/userguide/kieker-userguide.pdf,build/libs/*.jar', fingerprint: true
        }
      }
    }

    stage('Release Check Extended') {
      when {
        branch 'master'
      }
      steps {
        dir(env.WORKSPACE) {
          echo "We are in master - executing the extended release archive check."
          sh './gradlew checkReleaseArchives -x test -x check '
        }
      }
    }

    stage('Push to Stable') {
      when {
        branch 'master'
      }
      steps {
        dir(env.WORKSPACE) {
          echo "We are in master - pushing to stable branch."
          sh 'git push git@github.com:kieker-monitoring/kieker.git $(git rev-parse HEAD):stable'
        }
      }
    }
  }

  post {
    always {
      deleteDir()
    }

    //changed {
    //mail to: 'ci@kieker-monitoring.net', subject: 'Pipeline outcome has changed.', body: 'no text'
    //}

    //failure {
    //mail to: 'ci@kieker-monitoring.net', subject: 'Pipeline build failed.', body: 'no text'
    //}

    //success {
    //}

    //unstable {
    //}
  }
}
