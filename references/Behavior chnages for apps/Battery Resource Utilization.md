# Battery Resource Utilization

Android 13では以下のバッテリー保護対策が導入される。

* システムがアプリをApp Standby Bucketの`restricted`に移動するルールが更新
  * APIを呼び出すことでアプリがどのバケットに置かれているかを確認できる
* ユーザーがアプリをバックグラウンドでのバッテリー使用を制限する状態にした時にアプリが実行できる作業に関する新しい制限
* バックグラウンドでの過剰なバッテリー使用や長時間稼働するForeground Serviceについてユーザーに警告する新しいシステム通知

## Updates to when an app enters the "restricted" App Standby Bucket

アプリが対象外とならない限りアプリに以下の動作があった場合はシステムは`restricted`に移動する。

* ユーザーが8日間アプリを操作しない。ユーザーがアプリのサービスにバインドする別のアプリとやりとりした場合はシステムはアプリを利用済みとみなす
  Note：デバイスの電源が切れている期間はカウントされない
* アプリが24時間以内に過剰な数のブロードキャストまたはバインディングを呼び出している
* 24時間以内にデバイスのバッテリーを消費した。閾値はRAMによって異なる場合がある。デバイスのバッテリー寿命に対するアプリを測定する場合、システムはアプリが以下のようないくつかの異なる場所で行う作業を考慮する。
  * expeditedを含むJob
  * BroadcastReceiver
  * Background Service
  * アプリのプロセスをシステムがキャッシュしているかどうか

### User interaction allows your app to exit "restricted" bucket

以下の方法でユーザーがアプリを操作するとシステムは`restricted`から移動させる。

* ユーザーがアプリが送信したNotificationをタップする
  Note：ユーザーがNotificationをすワイプで消した場合は例外
* ユーザーがアプリのウィジェットを操作する
* ユーザーがmedia buttonを操作しForeground Serviceに影響を与える
* ユーザーがAndroid Automotive OS経由でアプリに接続する
* アプリがPiPモードで表示されている
* アプリが画面嬢のアクティブなアプリの一つである

## New limitations for restricted background battery usage

Note：この変化は`targetSdkVersion`をAndroid 13に変更した時のみ影響する

従来のAndroidではバックグラウンドで動作するアプリの作業量を調整することができる。システム設定ないの「バッテリーの使用状況」画面には次のオプションが表示される。

* Unrestricted：バックグラウンドでの作業を許可するがより多くのバッテリーを消費する可能性がある
* Optimized(デフォルト)：ユーザーとアプリの利用に基づいて、アプリがバックグラウンドで実行する能力を最適化する
* Restricted：アプリの動作よりもバッテリー駆動時間を優先し、バックグラウンドでの作業により多くの制限をかける

Note：ユーザーがアプリを`restricted`状態にしアプリを起動した場合、一時的に`optimized`として扱う。ユーザーがアプリの操作をやめ別のアプリを操作すると再度`restricted`にする。

Android 9より`restricted`状態のアプリには以下のような制限がある。

* Foreground Serviceが起動できない
* 既存のForeground Serviceが削除される
* アラームが発生しない
* Jobが実行されない
* Android 13をターゲットにしている場合はアプリが起動されるまで以下を送信しない
  * `BOOT_COMPLETED`
  * `LOCKED_BOOT_COMPLETED`

## System notification for excessive background battery usage

Android 13ではアプリが24時間以内に端末のバッテリーを大量に消費した場合に表示されるシステム通知が導入される。この通知は`targetSdkVersion`に関係なくAndroid 13で動作するデバイス上の全てのアプリに表示される。

Note：アプリがForeground Serviceに関連する通知を表示しているときにシステムがアプリの高いバッテリー消費を検知した場合、システムはユーザーが通知を破棄するか、Foreground Serviceが終了するまで待機し、アプリが大量のバッテリーを消費し続ける場合にのみ通知を表示する。

アプリがデバイスのバッテリー寿命に与える影響を測定する際、システムは以下のいくつかの異なる場所でアプリが行う作業を考慮する。

* Foreground Service
* expeditedを含むタスク
* BroadcastReceiver
* バックグラウンドService
* アプリのキャッシュ

この通知が表示された場合、24時間経つまでは同じデバイスには表示されない。

## System notification for long-running foreground service

アプリがForeground Serviceが24時間以内に20時間以上実行していることをシステムが検知すると、通知を表示し、FGS Task Managerで操作するように促す。この通知には以下のテキストが含まれる。

`APP is running in the background for a long time. Tap to review.`

Note：システムがアプリに対してこの通知を表示した場合、少なくとも30日経つまでは表示されない。

Foreground Serviceのタイプが`FOREGROUND_SERVICE_MEDIA_PLAYBACK`, `FOREGROUND_SERVICE_TYPE_LOCATION`の場合はこの通知は表示されない。

さらにアプリが`FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK`, `FOREGROUND_SERVICE_TYPE_LOCATION`のいずれかのForeground Serviceを24時間以内に4時間以上実行するとシステムはアプリが起動するForeground Serviceに対して通知を送信しなくなる。

## Exemptions

以下はAndroid 13で導入されたすべてのバッテリー保護対策が免除される。

* システムアプリ、システムバインドアプリ
* コンパニオンデバイス用のアプリ
* デモモード端末で動作するアプリ
* デバイスオーナーアプリ
* プロファイルオーナーアプリ
* 永続的なアプリ
* VPNアプリ
* `ROLE_DIALER`を持つアプリ
* ユーザーがシステム設定で明示的に`unrestricted`に指定したアプリ

以下はアプリが`restricted`に入ることが免除され、8日間の非アクティブトリガーを回避できる。

* アクティブなウィジェットがある
* 以下のパーミッションのうちいずれか一つが付与されている
  * `SCHEDULE_EXACT_ALARM`
  * `ACCESS_BACKGROUND_LOCATION`

以下の状況ではAndroid 13で導入されたバッテリー保護対策が免除されるが、長時間実行されるForeground Serviceの通知をシステムが送信することは妨げられない。

* 進行中なアクティブな`MediaSession`がある
* `ACCESS_FINE_LOCATION`が付与されている

## Testing

### Prevent background use

以下のコマンドを実行するとアプリはバックグラウンドで実行できなくなる。

`adb shell cmd appops set PACKAGE_NAME RUN_ANY_IN_BACKGROUND deny`

### Place app into restricted bucket

以下のコマンドでアプリを強制的に`restricted`状態にする。

`adb shell am set-standby-bucket PACKAGE_NAME restricted`
