# Foreground Services (FGS) Task Manager

Android 13では`targetSdkVersion`に関係なく通知ドロワーからForeground Serviceを停止することができる。FGSタスクマネジャーと呼ばれ、Foreground Serviceを実行しているアプリのリストを表示する。

## User action stops your entire app

FGSタスクマネージャーで停止ボタンを押すとForeground Serviceだけでなくアプリ全体も停止する。

### Comparing behavior with "swipe up" and "force stop" user actions

Recent AppsでのSwipe upでは

* 履歴からの削除
* Activityバックスタックの削除

が行われ通知には影響はないが、FGS Task Managerはアプリ、及び、通知が停止する。
ただし、Activityバックスタックの削除が行われない。

### No callbacks sent when user stops app from FGS Task Manager

FGSタスクマネージャーで停止ボタンを押してもシステムはアプリにコールバックを送信しない。アプリ再開時に`ApplicationExitInfo` APIで終了理由をチェックすることは可能。

## System prompts related to long-running foreground services

アプリがForeground Serviceを長時間(24時間以内に20時間以上)実行していることをシステムが検出した場合、システムはユーザーに通知を送信し、FGS Task Managerと対話するよう促す。

詳細は https://developer.android.com/about/versions/13/changes/battery#system-notification-long-running-fgs

## Exemptions

システムは特定の種類のアプリに対して以下のセクションで説明するいくつかの免除レベルを提供している。

免除はアプリ単位でありプロセス単位ではない。システムがアプリ内の1つのプロセスを除外した場合、そのアプリの他のプロセスも除外される。

Note：アプリがデバイスにプリインストールされている場合でも免除の対象になるためには以下のセクションにある基準の少なくとも1つは満たす必要がある。

### Exemptions from appearing in the FGS Task Manager at all

以下のアプリはFGS Task Managerに全く表示されない。

* システムレベルのアプリ
* `ROLE_EMERGENCY`を持つアプリ
* デバイスがデモモードにある

### Exemptions from being stoppable by users

以下のアプリはFGS Task Managerに表示されるが停止ボタンは表示されない。

* デバイスオーナーアプリ
* プロファイルオーナーアプリ
* `android:persistent=true`なアプリ
* `ROLE_DIALER`を持つアプリ

## Testing

`adb shell cmd activity stop-app PACKAGE_NAME` で停止中、停止後にアプリが期待通りに動作しているかをテストできる。
