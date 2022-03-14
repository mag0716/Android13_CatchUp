# Behavior changes: Apps targeting Android 13 or higher

## Privacy

### New runtime permission for nearby Wi-Fi devices

以前のバージョンでホットスポット、Wi-Fi Direct接続、Wi-Fi RTTなどのユースケースを完了するためには`ACCESS_FINE_LOCATION`を扶養する必要がある。

ユーザーが位置情報の許可とWi-Fi機能を関連づけるのは困難なため、Android 13では`NEARBY_DEVICES`グループの`NEARBY_WIFI_DEVICES`が導入され、上記のユースケースを満たすようになる。

アプリがWi-Fi APIから位置情報を必要としない限りはAndroid 13をターゲットにしたアプリでは`ACCESS_FINE_LOCATION`の代わりに`NEARBY_WiFI_DEVICES`をリクエストする必要がある。これはAndroid 12以降でBluetoothデバイス情報を位置情報に使用しないことを主張する処理と同様。

Note: この変更は`ACCESS_FINE_LOCATION`を必要とするWi-Fi APIを呼び出すアプリに影響する。APIの一覧は https://developer.android.com/about/versions/13/features/nearby-wifi-devices-permission#check-for-apis-that-require-permission

詳細は、https://developer.android.com/about/versions/13/features/nearby-wifi-devices-permission を参照

## Security

### Intent filters block non-matching intents

既存の`intent filter`はコンポーネントに配信されるIntentをフィルタリングせずに暗黙的Intentの解決時のみに利用され直感的ではない。アプリが後悔したコンポーネントをマニフェストに登録し、`<intent-filter>`を追加するとコンポーネントは任意の`intent filter`に一致しないものでもIntentで起動できるようになる。

コンポーネントが開始される時にIntentをチェックしないと状況によってはアプリの内部であるはずの機能を外部アプリがトリガーすることが可能になってしまう。

変更前はコンポーネントが宣言した`<intent-filter>`要素に一致しないIntentに配信する方法は以下の2通り

1. 明示的なIntent
1. Intent Selector：一致するIntentをメインIntentのセレクタとして設定した場合、メインIntentが常に配信される

Android 13以降をターゲットするアプリでは外部アプリから発信されたすべてのIntentが宣言された`<intent-filter>`にマッチする場合のみ配信されるようになる。

マッチしないIntentはブロックされるが、Intentのマッチングが矯正されない例外は以下。

* intent filter を宣言していないコンポーネントに配信されるIntent
* 同一アプリ内、スステム、ルートから発信されたIntent
