#!/bin/sh

## common
sudo apt install clang libicu-dev libcurl3 libpython2.7 binutils fpc nodejs unzip zip build-essential gobjc gobjc++ gnustep gnustep-devel libgnustep-base-dev golang-go curl vim

## swift
cd ~
mkdir Develop
cd Develop

#swift
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
cd ~

## kotlin
curl -s https://get.sdkman.io | bash
source .sdkman/bin/sdkman-init.sh
sdk install kotlin
cd /usr/bin
sudo ln -s ~/.sdkman/candidates/kotlin/current/bin/kotlin kotlin
sudo ln -s ~/.sdkman/candidates/kotlin/current/bin/kotlinc kotlinc

## some hints
# echo "please add swift path with: $ export PATH=~/Develop/swift/usr/bin:\"${PATH}\""
