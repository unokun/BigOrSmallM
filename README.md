# BigOrSmallM

## maven
### jarファイルを作成する

依存ライブラリ(jar)を含むパッケージ(jar)ファイルを作成する。

- [依存するjarを含んだバッチ用jarファイルをMavenでビルドする手順](http://takemikami.com/archives/1419/)

## Commandline parser

## Java Reflection API
ユニットテストでprivateメソッドをテストする。
```Java
  Method method = テスト対象クラス.class.getDeclaredMethod(メソッド名, 引数の型);
  method.setAccessible(true);
  method.invoke(テスト対象クラスのインスタンス, 引数);
```
クラスのメソッド一覧
```
  // メソッド情報
  for (Method m : Trump.class.getMethods()) {
    // パラメータ情報
    for (Parameter p : m.getParameters()) {
    }
  }
```
# Gradle Project
- [Gradle使い方メモ - Qiita](http://qiita.com/opengl-8080/items/4c1aa85b4737bd362d9e)
- [IntelliJGradleのプロジェクト作成方法メモ - Qiita](http://qiita.com/WK6_8B/items/77f3b49fc0b7d4c4ff27)
- [IntelliJ IDEA 入門 e- Qiita](http://qiita.com/opengl-8080/items/108102d692b49f804dbd)
