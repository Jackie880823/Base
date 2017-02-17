#!/bin/bash

# 将美工切好的按设定好的倍数放置对应的drawable资源目录中

# 查找png文件
find $1 -name "*.png" | while read file
do
    # 目标
    TARGET=./app/src/main/res
    filter=""
    case "${file##*/}" in
        *@1.5x*)
            # 1.5倍大小资源图
            TARGET=${TARGET}/drawable-hdpi
            filter=@1.5x
            ;;
        *@2x*)
            # 2倍大小资源图
            TARGET=${TARGET}/drawable-xhdpi
            filter=@2x
            ;;
        *@3x*)
            # 3倍大小资源图
            TARGET=${TARGET}/drawable-xxhdpi
            filter=@3x;;
        *@4x*)
            # 4倍大小资源图
            TARGET=${TARGET}/drawable-xxxhdpi
            filter=@4x
            ;;
        *)
            # 其余都认为是原始大小资源图
            TARGET=${TARGET}/drawable-mdpi
            ;;
    esac


    #如果文件夹不存在，创建文件夹
    if [ ! -d $TARGET ]; then
        echo "mkdir ${TARGET}"
        mkdir $TARGET
    fi

    if [ ! -n $2 ]; then
        # 没有输入文件名，不需要修改文件名，去掉过滤的字符串
        FILE_NAME=${file##*/}
        FILE_NAME=${FILE_NAME/${filter}/""}
    else
        # 第二个参数为拷贝后的文件名
        FILE_NAME=$2
    fi

    # 确定最终于的文件存放目录
    TARGET=${TARGET}/${FILE_NAME}

    # 将文件拷贝到目标目录中
    echo "copy ${file} to ${TARGET}"
    cp $file ${TARGET}
done
