#!/bin/bash
set -ev
# start lib deployment  ...
mnn deploy -Pdeploy,sonatype --settings .travis/settings.xml -DskipTests=true -B -q -U


# start doc deployment ...

# curren folder
pwd
#travis home
echo $HOME
git status


# clear existsing doc files 
#rm -rf docs
#mkdir docs

# build javadoc 
#mvn javadoc:aggregate  -DreportOutputDirectory=docs

# Swich to the Travis build directory, configure git and clone the repo
#cd $HOME
#git config --global user.email "travis@travis-ci.org"
#git config --global user.name "travis-ci"

# Commit and Push the Changes

#git add docs
#git commit -m "Lastest javadoc on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages"
#git push
#echo ### deployment successfully finished
