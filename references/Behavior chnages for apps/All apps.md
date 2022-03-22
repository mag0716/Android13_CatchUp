# Behavior changes: all apps

## Performance and battery

### Foreground Services(FGS) Task Manager

Android 13は`targetSdkVersion`に関係なく通知ドロワーからForeground Serviceを停止することができる。FGSタスクマネージャは現在のForeground Serviceを実行しているアプリのリストが表示される。このリストには「Active apps」というラベルが貼られている。

Caution：ユーザーが停止ボタンを押すと、Foreground Serviceだけでなくアプリ全体が停止する。

詳細は https://developer.android.com/about/versions/13/changes/fgs-manager

### Improve prefetch job handling using JobScheduler

JobSchedulerはアプリがユーザー体験を改善するために次のアプリ起動近く、もしくは前に実行する特定のジョブを「prefetch」ジョブとしてマークする方法を提供する。(JobInfo.Builder.setPrefetch())

Android 13ではシステムはアプリが次に起動される時間を判断しようとし、その推定値をプリフェッチジョブの実行に使用する。

### Battery Resource Utilization

Android 13では Battery Resource Utilizationが導入されシステムがデバイスのバッテリー寿命をよりよく管理するための方法をいくつか提供する。

* システムがアプリをApp Standby Bucketの`restricted`に配置する際のルールを更新
* ユーザーがアプリをバックグランドでのバッテリー使用を制限した場合にアプリが行える作業に新たな制限が加わる
* 過剰なバッテリー使用と長時間実行されるForeground Serviceをユーザーに警告する

## Privacy

### Runtime permission for notifications

Android 13では、通知に関する`POST_NOTIFICATIONS`の実行時権限が導入される。この変更によりユーザーは自分にとってもっとも重要な通知に集中することができる。

Note：メディアセッションに関連する通知はこの動作の変更から除外される

この機能を追加制御と柔軟性の効果を得るためにできるだけ早くAndroid 13をターゲンットにすることを強く推奨。

詳細は https://developer.android.com/training/permissions/usage-notes

## User experience

### In-app language pickers

現在アプリ内で独自に言語設定を可能にしているアプリは代わりにアプリごとの言語設定のための新しいAPIを使用する必要がある。新しいAPIを使用するとユーザーがアプリ内の言語設定で選択しているか、システム設定で選択しているかに関わらずユーザーが希望する言語でアプリを表示できるようになる。
