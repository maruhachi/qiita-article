# タイトル
Koltinでもトラブル解析してみたい!

# タグ
Koltin,

# 本文

## 予定

デッドロックによるスタック
および
無限ループによるOOM

上記のトラブル解析をしてみる

トラブル例
https://www.ulsystems.co.jp/topics/039

` cat /dev/urandom | head -c 20m > test.txt`

jvisualvm(jdk1.8)

$ java -Xmx500m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/ykonno/Src/qiita-article/ -jar build/libs/infinite-loooooop-0.0.1-SNAPSHOT.jar

これらをKotlinクラス、Javaクラスそれぞれでつくってみたい

解析方法
https://qiita.com/FSMS/items/8239e61d7fbbd4cca5c4
