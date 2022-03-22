# Behavior changes: Apps targeting Android 13 or higher

## Privacy

### Notification permission affects foreground service appearance

ユーザーが通知権限を拒否した場合、これらのForeground Serviceに関連する通知はFGS Task Managerに表示されるが通知ドロワーに表示されることはない。

### New runtime permission for nearby Wi-Fi devices

以前のバージョンでホットスポット、Wi-Fi Direct接続、Wi-Fi RTTなどのユースケースを完了するためには`ACCESS_FINE_LOCATION`を扶養する必要がある。

ユーザーが位置情報の許可とWi-Fi機能を関連づけるのは困難なため、Android 13では`NEARBY_DEVICES`グループの`NEARBY_WIFI_DEVICES`が導入され、上記のユースケースを満たすようになる。

アプリがWi-Fi APIから位置情報を必要としない限りはAndroid 13をターゲットにしたアプリでは`ACCESS_FINE_LOCATION`の代わりに`NEARBY_WiFI_DEVICES`をリクエストする必要がある。これはAndroid 12以降でBluetoothデバイス情報を位置情報に使用しないことを主張する処理と同様。

Note: この変更は`ACCESS_FINE_LOCATION`を必要とするWi-Fi APIを呼び出すアプリに影響する。APIの一覧は https://developer.android.com/about/versions/13/features/nearby-wifi-devices-permission#check-for-apis-that-require-permission

詳細は、https://developer.android.com/about/versions/13/features/nearby-wifi-devices-permission を参照

### Use of body sensors in the background requires new permission

Android 13では心拍数、体温、血中酸素濃度などのセンサーに対して「利用中」アクセスの概念が導入されている。これはAndroid 10で導入された位置情報に対するものと類似している。

もし、Android 13をターゲットにする場合は、バックグラウンドでこれらの情報にアクセスが必要な場合は、`BODY_SENSORS`だけでなく`BODY_SENSORS_BACKGROUND`を宣言する必要がある。

Note：これは`hard-restricted`と呼ばれデバイスのインストーラがアプリの権限を許可するまでアプリが保持することはできない。詳細は https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/permission/Permissions.md#restricted-permissions

## Security

### Intent filters block non-matching intents

アプリがAndroid 13以降をターゲットとする他のアプリのエクスポートされたコンポーネントにIntentを送信すると受信アプリの`<intent-filter>`要素にマッチする場合にのみ`Intent`が配信される。つまり以下を除きマッチされない`Intent`を全てブロックする。

* 他のアプリのコンポーネントに送信された`Intent`でそれらのコンポーネントが`intent-filter`を宣言していない場合
* 自分のアプリ内の他のコンポーネントに送信された`Intent`
* システムから送信された`Intent`
* ルートレベルの特権を持つユーザーから送信された`Intent`

### Safer exporting of context-registered receivers

ランタイムレシーバーの安全性を高めるためAndroid 13ではアプリ内の特定のBroadcastReceiverをエクスポートしてデバイス上の他のアプリから見えるようにするかどうかを指定することができる。BroadcastReceiverがエクスポートされると他のアプリが保護されていないブロードキャストがアプリに送信される可能性がある。Android 13意向を対象とするアプリで利用可能になり、アプリの脆弱性の主な原因の1つを防ぐのに役立つ。

Androidの以前のバージョンではデバイス上の任意のアプリはレシーバーが署名許可によって保護されていない限り動的に登録されたレシーバーに送信することができた。

安全性を高めるために以下に従う。

* `DYNAMIC_RECEIVER_EXPLICIT_EXPORT_REQUIRED`を有効にする
* アプリの各BroadcastReciverで他のアプリがそのあぷりに送信できるかどうかを明示的に指定する

Caution：`DYNAMIC_RECEIVER_EXPLICIT_EXPORT_REQUIRED`を有効にすると、`RECEIVER_EXPORTED`か`RECEIVER_NOT_EXPORTED`を指定する必要がある。指定していない場合は`SecurityException`がスローされる。

この状況を検出するためにlintルールがあり、今後のリリースでチェックされる予定。

## Performance and battery

## Battery Resource Utilization

Android 13をターゲットとするアプリでユーザーがバックグラウンドでのバッテリー使用を制限状態にした場合、システムはブロードキャストに関連するいくつかの制限を適用する。

詳細は https://developer.android.com/about/versions/13/changes/battery#restricted-background-battery-usage
