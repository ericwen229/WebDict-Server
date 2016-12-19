###主机URL：115.159.0.12:8080

单词查询：

* 方法：GET
* URL：/word/q
* 参数：
  * word：需查询的单词，不可缺省
  * baidu：是否启用百度查询，缺省为false（不启用，下同）
  * youdao：是否启用有道查询，缺省为false
  * jinshan：是否启用金山查询，缺省为false
* 结果`application/json`示例：

  ```json
    {
      "word": "foo",
      "explanations": [
        {
          "source": "youdao",
          "status": "success",
          "enPhonetic": "en youdao foo",
          "usPhonetic": "us youdao foo",
          "translation": "tr youdao foo",
          "likes": "1"
        },
        {
          "source": "jinshan",
          "status": "success",
          "enPhonetic": "en jinshan foo",
          "usPhonetic": "us jinshan foo",
          "translation": "tr jinshan foo",
          "likes": "0"
        }
      ]
    }
  ```

---

点赞/取消点赞：

* 方法：POST
* URL：/word/like
* 参数：
  * word：需点赞的单词，不可缺省
  * source：单词来源，不可缺省
  * dislike：是否取消点赞，缺省为false
* 结果`application/json`示例：

  ```json
    {
      "status": "success",
      "msg": ""
    }
  ```

---

注册：

* 方法：POST
* URL：/user/signup
* 参数：
  * username：用户名，不可缺省
  * pwd：密码，不可缺省
* 结果`application/json`示例：

  ```json
    {
        "status": "fail",
        "msg": "user already exists"
    }
  ```

---

登录：

* 方法：POST
* URL：/user/login
* 参数：
  * username：用户名，不可缺省
  * pwd：密码，不可缺省
* 结果`application/json`示例：

  ```json
    {
      "status": "fail",
      "msg": "user not found"
    }
  ```

---

登出：

* 方法：POST
* URL：/user/logout
* 参数：
  * username：用户名，不可缺省
  * pwd：密码，不可缺省
* 结果`application/json`示例：

  ```json
    {
      "status": "success",
      "msg": ""
    }
  ```

---

获取用户列表：

* 方法：GET
* URL：/user/list
* 参数：无
* 结果`application/json`示例：

  ```json
    {
      "onlineUsers": [
        "foo",
        "bar"
      ],
      "offlineUsers": [
        "foobar",
        "barfoo"
      ]
    }
  ```

---

获取用户状态：

* 方法：GET
* URL：/user/status
* 参数：
  * username：查询的用户名，不可缺省
* 结果`application/json`示例：

  ```json
    {
      "status": "success",
      "userOnline": "true"
    }
  ```

---

发送单词卡

* 方法：POST
* URL：/card/send
* 参数：
  * fromUser：发送方，不可缺省
  * toUser：接收方，不可缺省
  * word：发送的词，不可缺省
  * youdao：是否包括有道词典释义，缺省为true
  * jinshan：是否包括金山词典释义，缺省为true
  * haici：是否包括海词词典释义，缺省为true
* 结果`application/json`示例：

  ```json
    {
      "status": "success",
      "msg": ""
    }
  ```

---

查询单词卡

* 方法：GET
* URL：/card/receive
* 参数：
  * username：接收方，不可缺省
* 结果`application/json`示例：

  ```json
    {
      "cards": [
        {
          "from": "foo",
          "word": "hello",
          "youdao": "true",
          "jinshan": "true",
          "haici": "true"
        },
        {
          "from": "bar",
          "word": "what",
          "youdao": "true",
          "jinshan": "false",
          "haici": "false"
        }
      ]
    }
  ```
