#!/bin/bash
set -ev
echo ### start deployment...
mvn deploy -Pdeploy,sonatype --settings .travis/settings.xml -DskipTests=true -B
echo ### deployment successfully finished


### start doc deployment ...

# checkout master branch
#git checkout master

# clear existsing doc files 
#rm -rf docs
#mkdir docs

# build javadoc 
#mvn javadoc:aggregate  -DreportOutputDirectory=docs

# Setup git
#git config --global user.email "travis@travis-ci.org"
#git config --global user.name "travis-ci"

# Commit and Push the Changes

#git status
#git add docs
#git status
#git commit -m "Lastest javadoc on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages"
#git status

# setup github access
#eval "$(ssh-agent -s)"
#git ssh-add ${env.GITHUB_KEY}

# deploy
#git push
### deployment successfully finished
