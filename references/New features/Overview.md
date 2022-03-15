# Features and APIs Overview

[API diff](https://developer.android.com/sdk/api_diff/t-dp1/changes)

## User experience

### Themed app icons

 Android 13から「Themed app icons」をオプトインすることが可能になり、アプリアイコンがユーザが選択した壁紙やテーマの色合いを継承するようになる。

 この機能をサポートするためにはアプリが単色のアプリアイコンを提供し、マニフェスト内の`<adaptive-icon>`から指定する必要がある。

#### Specifications

単色のアプリアイコンは以下の仕様を満たしている必要がある。

* VectorDrawableであること
* 90x90dpのコンテナの中に36x36dpの領域内に収まることが推奨。ロゴを大きくする場合は最大でも60x60dp
* ロゴはフラットなものが推奨。立体的な場合はアルファグラデーションの仕様が可能

たいていのアプリでは通知に利用したVectorDrawableを再利用することができる。

#### Implement a themed app icon

`<adaptive-icon>`内に`<monochrome>`を追加し、マニフェストファイルで`android:icon`に指定する。

Note:もし`roundIcon`と`icon`の両方を定義している場合は`roundIcon`は削除するか、`roundIcon`に指定しているファイルにも`monochrome`を追加する必要がある。

### Quick Settings placement API

カスタムタイルを提供するアプリでユーザーにクイック設定に追加してもらえるようにAPIが追加された。このAPIを利用することでシステムダイアログ経由でタイルを追加できるようになる。

### Better support for multilingual users

#### Per-app language preferences

マルチリンガルなユーザーはシステム言語を英語などの言語に設定していても特定のアプリで他の言語を選択したい場合が多くある。このようなユーザーがアプリでより良い体験をできるように、アプリで表示する言語を実行時に設定できるAPIが導入される。

アプリ内のカスタム言語ピッカーを使用しているアプリは新しいAPIを利用することで必要なコード量を削減することができる。

後方互換性のため Appcompat 1.6.0-alpha01 から AndroidX でも利用可能。

多言語をサポートしないアプリはこの変更の影響をうけない。

#### Unicode library updates

* en-CAとen-PHは翻訳リソースがない場合、en-GBではなくenを利用するようになる
* es, it, pt, pt-PTにmany pluralのカテゴリが導入された。

#### Faster hyphenation

ハイフネーションのパフォーマンスが200%最適化されレンダリングパフォーマンスにほとんど影響をあたえることなく、`TextView`で有効にできるようになった。より高速なハイフネーションを有効にするためには、`setHyphenationFrequency()`で新しい`fullFast`か`normalFast`を利用する。

## Privacy and security

### Photo picker

Android 13では新しいphoto pickerが導入され、ユーザーはアプリにアクセス権を与える代わりにプロフィール写真などアプリと共有する特定の画像やビデオを選択することができる。

photo pickerはアプリが実行権限を宣言する必要がないためユーザーのプラバシーを保護することができ、また標準化されたUIを利用することでより一貫したUXを実現する。

Note: photo pickerはユーザーの写真やビデオにアクセスする手法として推奨される

### New runtime permission for nearby Wi-Fi devices

Android 13はWi-Fi経由でデバイスの近くのアクセスポイントへの接続を管理するアプリのために`NEARBY_DEVICES`権限グループに`NEARBY_WIFI_DEVICES`が導入される。さらに物理的な位置情報を必要としない限り`ACCESS_FINE_LOCATION`の宣言が不要になる。

詳細は https://developer.android.com/about/versions/13/features/nearby-wifi-devices-permission

## Graphics

### Programmable shaders

Android Graphics Shading Languageを使用して定義された動作をもつプログラム可能な`RuntimeShader`オブジェクトのサポートが追加された。Androidの内部的ではリップルエフェクト、ブラー、オーバースクロールを実装しており、アプリ内に高度なエフェクトを作成することが可能になる。

## Core functionality

### OpenJDK 11 updates

ライブラリの更新とJava 11言語のサポートを両方行い、OpenJDK 11 LTSリリースに合わせる。Android 13で導入されたコアライブラリの変更はGoogle Playシステムアップデートを通じてAndroid 12端末でも利用できるようになる予定。

* `var`
* `isBlank()`
* `Optinal`などの`isPresentOrElse()`など
* `InputStream`, `OutputStream`などの`transferTo()`など
