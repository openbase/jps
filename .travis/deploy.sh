#!/bin/bash
set -ev
### start lib deployment  ...
#mvn deploy -Pdeploy,sonatype --settings .travis/settings.xml -DskipTests=true -B -q -U


### start doc deployment ...

# checkout master branch
git checkout master

# clear existsing doc files 
rm -rf docs
mkdir docs

# build javadoc 
mvn javadoc:aggregate  -DreportOutputDirectory=docs

# Setup git
git config --global user.email "travis@travis-ci.org"
git config --global user.name "travis-ci"

# Commit and Push the Changes

git status
git add docs
git status
git commit -m "Lastest javadoc on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages"
git status
# deploy
git push
echo ### deployment successfully finished
