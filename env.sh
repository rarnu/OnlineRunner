#!/bin/bash

## script for ubuntu

## common
sudo apt update
sudo apt install net-tools
sudo apt install gcc
sudo apt install perl
sudo apt install make
sudo apt install clang
sudo apt install libicu-dev
sudo apt install libcurl3
sudo apt install libpython2.7
sudo apt install binutils
sudo apt install fpc
sudo apt install nodejs
sudo apt install npm
sudo apt install unzip
sudo apt install zip
sudo apt install build-essential
sudo apt install gobjc
sudo apt install gobjc++
sudo apt install gnustep
sudo apt install gnustep-devel
sudo apt install libgnustep-base-dev
sudo apt install golang
sudo apt install curl
sudo apt install vim
sudo apt install php
sudo apt install llvm
sudo apt install libblocksruntime-dev
sudo apt install libdispatch-dev
sudo apt install openjdk-8-jdk
sudo apt install ruby
sudo apt install scala
sudo apt install lua5.2
sudo apt install r-base
sudo apt install mono-devel
sudo apt install fsharp

## swift
cd ~
mkdir Develop
cd Develop

wget https://swift.org/builds/swift-5.0.1-release/ubuntu1804/swift-5.0.1-RELEASE/swift-5.0.1-RELEASE-ubuntu18.04.tar.gz
wget https://swift.org/builds/swift-5.0.1-release/ubuntu1804/swift-5.0.1-RELEASE/swift-5.0.1-RELEASE-ubuntu18.04.tar.gz.sig
wget -q -O - https://swift.org/keys/all-keys.asc | gpg --import -
gpg --keyserver hkp://pool.sks-keyservers.net --refresh-keys Swift
gpg --verify swift-5.0.1-RELEASE-ubuntu18.04.tar.gz.sig
tar zxf swift-5.0.1-RELEASE-ubuntu18.04.tar.gz
mv swift-5.0.1-RELEASE-ubuntu18.04/ swift/
# rm swift-5.0.1-RELEASE-ubuntu18.04.tar.gz
cd /usr/bin
sudo ln -s ~/Develop/swift/usr/bin/swift swift

## julia
cd ~/Develop
wget https://julialang-s3.julialang.org/bin/linux/x64/1.1/julia-1.1.0-linux-x86_64.tar.gz
tar zxf julia-1.1.0-linux-x86_64.tar.gz
cd /usr/local/bin
sudo ln -s ~/Develop/julia-1.1.0/bin/julia julia

## kotlin
cd ~
curl -s https://get.sdkman.io | bash
source .sdkman/bin/sdkman-init.sh
sdk install kotlin
cd /usr/bin
sudo ln -s ~/.sdkman/candidates/kotlin/current/bin/kotlin kotlin
sudo ln -s ~/.sdkman/candidates/kotlin/current/bin/kotlinc kotlinc

## typescript
npm install -g typescript

## dart
sudo apt-get install apt-transport-https
sudo sh -c 'curl https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -'
sudo sh -c 'curl https://storage.googleapis.com/download.dartlang.org/linux/debian/dart_stable.list > /etc/apt/sources.list.d/dart_stable.list'
sudo apt-get update
sudo apt-get install dart