# Per-app language preferences

多言語を使用するユーザーはシステム言語で英語などの1つの言語に設定していても、特定のアプリで他の言語を洗濯したい場合が多くある。このユーザーに対してアプリがより良い提供できるようにAndroid 13では多言語をサポートするアプリに以下の新機能を提供する。

* アプリのUIが利用する言語を実行時に設定するAPI
  * アプリ内のカスタム言語ピッカーを使用しているアプリはこれらの新しいAPIを使用してユーザーがどこで言語設定をしても一貫したUXが得られるようにする必要がある。また、新しいAPIは定型的なコード量を減らすことができる。後方互換性のためAppcompat 1.6.0-alpha01から利用可能。
* アプリごとに言語を設定するシステム設定

## API Implementation

アプリ内のカスタム言語ピッカーを使用していないアプリの場合、追加作業は必要ない。

アプリ内言語ピッカーがある、または使用したいアプリでは新しいAPIを利用してユーザーの優先言語の設定と取得を処理する。

### Implement using the AndroidX support library

言語ピッカーでユーザに選択してもらった言語を`setApplicationLocales()` を利用して設定する。

### Implement using the Android framework APIs

AndroidフレームワークのAPIを利用して、`setApplicationLocales()`, `getApplicationLocales()`を用いてアプリ内言語ピッカーを実装することも可能。

言語選択ツールに表示するユーザーの現在の優先言語を取得するために、アプリはシステムからその値を取得できる。

## System settings for users

ユーザーはシステム設定でアプリごとに言語を設定できる。設定には以下の2つの方法がある。

* Settings -> System -> Languages & Input > App Languages -> (select an app)
* Settings -> Apps -> (select an app) -> Language

## Known issues

テスト時には以下の既知の問題があることに注意する。

* 利用可能な言語のリストにはアプリがサポートする言語が反映されていない場合がある
* アプリが分割されたapkを利用している場合、アプリのロケールが変更されてもそれらのapkは自動的にダウンロードされない
* UIは暫定版で今後変更される予定
