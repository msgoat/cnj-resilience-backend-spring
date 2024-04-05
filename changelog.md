# Changelog
All notable changes to `cnj-resilience-backend-spring` will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [4.0.0] - 2024-04-05
### Added
- Tagging of git branch
### Changed
- upgraded Spring Boot to version 3.2.4
- upgraded Java to version 21
- upgraded Maven plugins and dependencies
- consolidated POM with other showcases
- consolidated system tests with other showcases
- commit-stage builds produce Docker images for linux/amd64 and linux/arm64/v8 platforms now
- Docker images use Generational Z garbage collector by default
- consolidated common dependencies
- upgraded common cloudtrain dependencies
- Now a helm chart is packaged and pushed as an artifact during the commit-stage build
- Now the helm chart is pulled before deploying during the integration-test-stage build

## [3.0.0] - 2023-06-11
### Changed
- moved to new AWS CodeBuild pipeline
- moved to new AWS EKS cluster
- upgraded everything

## [2.0.0] - 2023-02-23
### Changed
- upgraded to Java 17
- upgraded to Spring Boot 3.0.2
- consolidated with hello world spring boot use case

## [1.1.0] - 2022-09-09
### Fixed
- added JSON logging
### Changed
- improved system test WelcomeEndpointSystemTest in order to verify all resilience options

## [1.0.0] - 2022-09-02
### Added
### Changed
- first re-release after repository split
