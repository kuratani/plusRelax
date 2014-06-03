+Relax
==================
Salesforce1 Platform(特にForce.com Platform)でのTDDに癒やしをもたらすアプリケーション

はじめに
--------
Force.com Platform開発でテストで長い時間待って挙句テスト失敗したときにイライラを感じたことはありませんか？

+Relaxでは、癒やし効果抜群のかわいい猫の鳴き声のChatter投稿でなんとなくテスト結果を通知してくれます！
テスト結果を解析するために最新鋭のRelaxEngineを搭載し、テスト結果をなんとなくわかるよう鳴き声変換します。
また、デバッグモードで動作させると、テスト成功・失敗・カバレッジ75%未満をわかるようになり、テスト件数、LOCなどを把握できます(非推奨)。

インストール方法
--------
1. 下記に記載されているツールをインストールする
1. Apache Antを実行できるようにする
1. 下記に記載されているライブラリのjarファイルにCLASSPATHを設定する(例えば、ANT_HOME/lib以下にコピーする)
1. Chatter Rest APIを利用するための接続アプリケーションを作成します。


build.xmlの作成
--------------- 
build.xmlを開き、projectルート要素に、**xmlns:plusRelax="antlib:jp.gr.java_conf.a_kura.ant.plusrelax"**という属性設定を以下のように追加します。

```xml
    <project name="Sample usage of Ant tasks" basedir="." xmlns:plusRelax="antlib:jp.gr.java_conf.a_kura.ant.plusrelax">
```

以下では、タスク定義のサンプルを紹介します。

```xml
    <target name="test">
		<plusRelax:runTest
			username="${sf.username}"
			password="${sf.password}"
			serverurl="${sf.serverurl}"
			clientId="${rp.clientId}"
			clientSecret="${rp.clientSecret}"
			chatUsername="${rp.chatUsername}"
			chatPassword="${rp.chatPassword}"
			chatServerUrl="${rp.chatServerUrl}"
			isDebug="${rp.isDebug}">
		</plusRelax:runTest>
    </target>
```

以下では、build.propertiesのサンプルを紹介します。

```
# build.properties

# Test Target Org 
sf.username = testuser1@example.org
sf.password = example1
sf.serverurl = https://login.salesforce.com

# Connected App Info
rp.clientId = XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
rp.clientSecret = 99999999999999

# Chatter Post Org
rp.chatUsername = testuser2@example.org
rp.chatPassword = example2
rp.chatServerUrl = https://login.salesforce.com

# Chatter Post Info
rp.isDebug = false
rp.engineName = 
rp.mentionUserId = 005xxxxxxxxxxxxxxx
rp.hashTag = +Relax
rp.contentSuccess = 069xxxxxxxxxxxxxxx
rp.contentFailure = 069yyyyyyyyyyyyyyy
rp.contentShortCoverage = 069zzzzzzzzzzzzzzz
```


サンプルソースコード
--------
フォルダsample以下にForce.comのソースコードとそのテストコードがあります。同フォルダにあるbuild.xmlとbuild.propertiesに必要な設定を記載して実行することで、+Relaxを試すことができます。
* success : テスト成功するサンプルソースコードです
* failure : テスト失敗するサンプルソースコードです
* short_coverage : テスト成功するが、カバレッジが75%未満のサンプルソースコードです


アーキテクチャ
--------
JavaVMで動作するJavaアプリケーションです。
Salesforce Migration Toolsを拡張し、Force.com組織のテストを実行できます。
テスト結果は、接続アプリケーションを介してOAuth認証し、Chatter REST API経由でChatter投稿します。
テスト結果から猫の鳴き声に変換するためにRelaxEngineを利用しています。


ライブラリ
--------
利用しているライブラリは以下のとおりです。
* [Apache Ant](http://ant.apache.org/) 対象jarファイル: ant.jar(コンパイル時に必要)
* [Apache Commons Logging](http://commons.apache.org/proper/commons-logging/) 対象jarファイル: commons-logging-1.1.3.jar(実行時に必要)
* [Apache HttpComponents](http://hc.apache.org/index.html) 対象jarファイル: httpclient-4.3.3.jar, httpcore-4.3.2.jar(コンパイル/実行時に必要)
* [Jackson](https://github.com/FasterXML/jackson) 対象jarファイル: jackson-annotations-2.2.3.jar, jackson-core-2.2.3.jar, jackson-databind-2.2.3.jar(コンパイル/実行時に必要)
* Salesforce Migration Tools 対象jarファイル: ant-salesforce.jar(コンパイル/実行時に必要)


ツール
--------
利用しているツールは以下のとおりです。
* [Oracle Java](http://java.com/ja/)
* [Apache Ant](http://ant.apache.org/) 利用バージョン:1.9.4
* Salesforce Migration Tools


+Relax タスクリファレンス
-------------------------------
runTestタスクは テストを実行するために使用されます。

<dl>
<dt>**username**</dt>
<dd>必須の属性。テスト実行するSalesforceへのログインに利用するユーザー名。</dd>

<dt>**password**</dt>
<dd>必須の属性。テスト実行するSalesforceへのログインに利用するパスワード。セキュリティトークンが必要な場合は、25桁のトークンをパスワードに続けて記述してください。</dd>

<dt>**serverurl**</dt>
<dd>任意の属性。デフォルトは'https://login.salesforce.com'。Sandbox(test.salesforce.com)でテスト実行する場合に指定します。</dd>

<dt>**runAllTests**</dt>
<dd>任意の属性(true/false)。デフォルトはtrue。trueを指定すると、デプロイ済みのすべてのApexテストが実行されます。</dd>

<dt>**clientId**</dt>
<dd>必須の属性。接続アプリケーションのコンシューマ鍵。</dd>

<dt>**clientSecret**</dt>
<dd>必須の属性。接続アプリケーションのコンシューマの秘密。</dd>

<dt>**chatUsername**</dt>
<dd>必須の属性。Chatter投稿するSalesforceへのログインに利用するユーザー名。</dd>

<dt>**chatPassword**</dt>
<dd>必須の属性。Chatter投稿するSalesforceへのログインに利用するパスワード。</dd>

<dt>**chatServerUrl**</dt>
<dd>必須の属性。任意の属性。デフォルトは'https://login.salesforce.com'。Sandbox(test.salesforce.com)でChatter投稿する場合に指定します。</dd>

<dt>**isDebug**</dt>
<dd>任意の属性。デフォルトはfalse。trueを指定すると、デバッグモードでChatter投稿します。</dd>

<dt>**engineName**</dt>
<dd>任意の属性。RelaxEngineをカスタマイズする場合にクラス名(jp.gr.java_conf.a_kura.ant.plusrelax.engine.DebugRelaxEngine など)を指定します。</dd>

<dt>**mentionUserId**</dt>
<dd>任意の属性。Chatter投稿時にメンション指定する場合にメンション先となるユーザのIDを設定します。</dd>

<dt>**hashTag**</dt>
<dd>任意の属性。Chatter投稿時にハッシュタグをつける場合にハッシュタグの文字列を設定します。</dd>

<dt>**contentSuccess**</dt>
<dd>任意の属性。テスト成功時のChatter投稿に既存のコンテンツのリンクをつける場合にコンテンツのIDを設定します。</dd>

<dt>**contentFailure**</dt>
<dd>任意の属性。テスト失敗時のChatter投稿に既存のコンテンツのリンクをつける場合にコンテンツのIDを設定します。</dd>

<dt>**contentShortCoverage**</dt>
<dd>任意の属性。テストカバレッジ75%未満のChatter投稿に既存のコンテンツのリンクをつける場合にコンテンツのIDを設定します。</dd>

<dt>**class**</dt>
<dd>任意の子要素。実行するApexテストクラスのリスト。runAllTests="false"のときは最低1つの要素を指定します。各要素には属性"runTest"を指定することもできます(true(デフォルト)/false)。runTest="false"のクラスはテスト実行しません。</dd>
</dl>

ライセンス
--------
Copyright &copy; 2014 Akira.Kuratani.

[MIT License](http://www.opensource.org/licenses/mit-license.php)