# Photo picker

ユーザーがメディアファイルを選択するための安全なビルトインの方法を提供し、アプリがメディアライブラリ全体にアクセスすることを許可する必要がなくなる。

Note: 今後予定されている Google Playのシステムアップデートに含まれる見込みで、Android 11以上をターゲットするアプリへのサポートが追加される。

## Media selection

Photo pickerは表示と検索が可能なインタフェースでユーザーのメディアライブラリを日付順にひょうじする。ユーザーが見るべきメディアは写真のみ、またはビデオのみと指定できデフォルトで許可されるメディアの最大選択数は1に設定されている。

## Define sharing limitations

`android.provide.extra.PICK_IMAGES_MAX` は Photo pickerに表示されるメディアファイルの最大数を示す値でアプリが宣言できる。例えば、アカウントに必要なプロフィール写真のようなユースケースでは1を表示すると良い。

Note: 最大1枚にした場合はhalf screenとして表示される。

## Select multiple photos or videos

アプリのユースケースでユーザが複数の写真や動画を選択する必要がある場合、`EXTRA_PICK_IMAGES_MAX` を使用して Photo pickerに表示する画像の最大数を指定することができる。

最大ファイル数として指定できる最大数はプラットフォームの制限があることに注意が必要で、最大数は `MediaStore#getPickImagesMaxLimit()` を利用する。

## Handle the photo picker results

Photo pickerを起動後、`ACTION_PICK_IMAGES`を利用して結果を処理する。

デフォルトだとPhoto pickerは写真と動画の両方を表示するが、`setType()`でMIMEタイプを指定することにより写真のみ、動画のみでフィルタリングすることもできる。例えば動画のみを表示する場合は`video/*`を渡す。

Note: アプリはこのIntentから返された永続的なアクセス権を持っていないのでプロセスが終了するとアプリはURIへのアクセスを失う。
