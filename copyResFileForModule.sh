#!/bin/bash

# 将其它项目中的资源文件拷贝到app中

# 查找指定文件
find $1 -name "${2}*" | while read file
do
    # 目标
    TARGET=../app/src/main/res
    FileName="${file##*res/}"
    TARGET=${TARGET}/${FileName}
    echo "copy ${file} to ${TARGET}"
    cp ${file} ${TARGET}
done