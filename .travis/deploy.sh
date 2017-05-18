#!/bin/bash
set -ev
echo ### start lib deployment  ...
mvn deploy -Pdeploy,sonatype --settings .travis/settings.xml -DskipTests=true -B -q -U
echo ### start doc deployment ...



rm -rf docs
mkdir docs
mvn javadoc:aggregate  -DreportOutputDirectory=docs

# Get to the Travis build directory, configure git and clone the repo
cd $HOME
git config --global user.email "travis@travis-ci.org"
git config --global user.name "travis-ci"

# Commit and Push the Changes

git add docs
git commit -m "Lastest javadoc on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages"
git push
echo ### deployment successfully finished
