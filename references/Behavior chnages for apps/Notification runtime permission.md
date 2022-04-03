# Notification runtime permission

 Android 13ではアプリから許可されていない通知を送信するためのRuntime Permissionである`POST_NOTIFICATIONS`が導入される。この変更によってユーザーはもっとも重要な通知に集中することができる。

 この機能による制御と柔軟性の恩恵を受けるためにできるだけ早くAndroid 13をターゲットにすることが推奨。12L以下を使い続けるとアプリ内で許可を要求する機会が失われる。

## Use the new permission

アプリから通知権限をリクエストするためにはAndroid 13をターゲットにしRuntime Permissionと同じリクエストプロセスを実装する必要がある。

この権限はマニフェストファイルに定義する必要がある。

## App capabilities depend on user choice in permissions dialog

このダイアログではユーザーは以下のアクションが可能

* 許可する
* 許可しない
* どちらのボタンも押さずにダイアログから離脱する

Caution：Android 12L以下をターゲットにしているアプリでユーザーが一度でも「許可しない」を選択すると以下が発生するまでダイアログが表示されることはない。

* ユーザーがアプリを再インストールする
* アプリをアップデートしたAndroid 13をターゲットにする

### User selects "Allow"

ユーザーが許可するを選択するとアプリは以下の動作に従う

* Notification channels で許可された通知が送信される
* Foreground Serviceに関連する通知は通知ドロワーに表示される

### User selects "Don't allow"

ユーザーが許可しないを選択するとアプリは通知を送信できなくなる。特定のRoleを除き、全てのNotification channelsがブロックされる。この動作はユーザーが設定から通知を無効化するのと類似している。

### User swipes away from dialog

ユーザーが許可する、許可しないをいずれも選択せずにダイアログから離脱した場合は以下の動作となる。

* アプリに一時的な通知許可の資格がある場合は一時的に許可として扱われる
* アプリに一時的な通知許可の資格がない場合は通知は送信できない

## Effects on newly-installed apps

Android 13の端末にアプリをインストールすると、アプリの通知はデフォルトでオフになっている。アプリは通知許可をリクエストし、ユーザーが許可するまで通知するのを待つ必要がある。

許可ダイアログが表示されるタイミングはアプリの`targetSdkVersion`に基づく。

* Android 13以上：アプリは許可ダイアログを表示するタイミングを制御することができる。アプリがこの許可をする必要とする理由をユーザーに説明し許可を得るように促す
* Android 12L以下：通知チャンネルを作成するときに許可ダイアログが表示される

## Effects on updates to existing apps

Note：ユーザーが12L以下のデバイスにインストールし、通知を許可しているケースでAndroid 13のデバイスにバックアップ、復元でアプリを復元したケース

このケースではシステムはアプリを既存アプリとみなし通知を送信するための一時的な許可を取得する。新しい通知許可に伴う混乱を最小限にするためシステムはAndroid 13へのアップグレード前にユーザーがインストールしているアプリに対して通知許可を一時的に自動的に付与する。一時的な許可の長さはアプリの`targetSdkVersion`によって異なる。

アプリのターゲットがAndroid 13以降の場合、一時的な許可はアプリがActivityを初めて起動するまで継続する。

アプリは許可ダイアログを表示するタイミングを完全に制御できる。この機会にアプリがこの許可を必要とする理由をユーザーに説明し、許可するように促す。

アプリが12L以下の場合、一時的な許可はユーザーが通知許可の実行時ダイアログで明示的に選択するまで継続される。つまりユーザーが選択せずにダイアログから離脱した場合はシステムはアプリの一時的な許可を維持する。

## Eligibility for temporary permission grant

アプリが一時的な付与の対象となるには既存の通知チャンネルを持ち、12L以下を実行するデバイスでユーザーによって明示的にその通知が無効化されていないことが必要。

12L以下のデバイスでユーザーがアプリの通知を無効にした場合、デバイスがAndroid 13以上にアップグレードされてもその拒否は継続される。

## Exemptions

メディアセッションに関連する通知は動作変更の対象外

## Best practices

### Update your app's target SDK version

許可ダイアログの表示タイミングをより柔軟に変更するためにAndroid 13をターゲットにする。

### Wait to show notification permission prompt

ユーザーに許可を求める前にアプリに慣れしたしんでもらう。

新しいユーザーはアプリを探索し、個々の通知要求の利点を直接実感したいと思うかもしれない。ユーザーのアクションからパーミッションのダイアログをトリガーすることができる。以下は通知許可ダイアログを表示するのに適したタイミング例。

* ユーザーがアラートベルボタンをタップした
* ユーザーが誰かのSNSアカウントをフォローすることを選択した
* ユーザーがで前の注文をする

`shouldShowRequestPermissionRationale()`が`true`を返さない限りアプリは中央の画面を表示する必要はない。

また、アプリの起動時にリクエストを表示し、アプリの起動が3回目か4回目になった時のみ表示するように設定することもできる。

### Request the permission in context

アプリ内で通知許可を要求する時は正しい文脈で行い通知が何に使われるのか、なぜユーザーがオプトインする必要があるのかを明確にする必要がある。例えば、メールアプリでは新着メールごとに通知を送信するオプションやユーザーが唯一の受審者であるメールのみに通知を送信するオプションを設定することができる。

この機会を利用してアプリの意図に透明性を与えればユーザーは通知許可を与える可能性が高くなる。

### Check whether your app can send notifications

通知を送信する前に、`areNotificationsEnabled()`で通知が有効化されていることを確認する。

### Use the permission responsibly

通知の承認を受けたら責任をもって使用する。ユーザーはアプリが送信する毎日の通知回数を確認でき、いつでも許可を取り消すことができる。