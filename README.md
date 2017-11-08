# Manual de instalação 

# Instalação do APK

É preciso possuir um celular Android versão **4.4.0**  ou maior para executar o apk.

Caso queira apenas instalar o apk siga os passos abaixo:

1. No seu celular vá em **configurações** e procure pelo menu de **Segurança**.
2. Procure pela opção de **Fontes desconhecidas** e permita a instalação de APKs.
3. Baixe o **periocular-release.apk** e coloque-o na pasta downloads do ceu celular.
4. No celular entre na pasta **Downloads** e clique no APK.
5. Clique em instalar o APK.

# modificação do código

# Versão das ferramentas utilizadas

ferramenta | Versão
------------ | -------------
OpenCV Android SDK | openCV-3.2.0
Android Studio | v3.0
Android SDK build-tools | v23
Android SDK platform-tools | v23.0
Android Official NDK | android-ndk-r12b

* **OS:** Windows 10

# Instalando dependências

Primeiramente faça  download da [**versão 3.2 do opencv para android**](https://opencv.org/releases.html) e [**NDK oficial do Android**](https://developer.android.com/ndk/downloads/index.html)!
* Extraia a NDK para um diretorio simples e **sem espaços** como: *C:\work\android-ndk-r12b*
* Extraia o SDK do OpenCV.



### Ajustando os caminhos para o Java e o NDK:

1. Clique direito no **Este computador**, em **Propriedades** clique em **Configurações avançadas do sistema**.
2. Na janela que se abriu clique em **Avançado** e selecione a ultima opção **Variaveis de ambiente**.
3. Procure pela variavel **PATH** e clique em **Editar**
4. Clique em **Novo** e cole o diretório da sua pasta java como: *C:\Program Files\Java\jdk1.8.0_40\bin\*

5. Adicione o caminho do **NDK** como no passo 4! Coloque apenas o diretório raiz como: *C:\work\android-ndk-r12b*
6. Feito!

### Instalando Cmake para o Android SDK:

1. Abra **Android Studio** e clique em **Settings**
2. Va para **Appearance & Behavior** e clique em **System settings** procure por **Android SDK**!
3. Clique na aba **SDK Tools** e tenha certeza que **CMake** está instalado, se não instale-o!

# Criando o Projeto

### Criando o projeto android

1. Abra o **Android Studio** e clique em **New Projcet**
2. Tenha certeza de marcar a opção **Include C++ Support**:exclamation::exclamation::exclamation:
3. continue o setup normalmente e clique em **Finish** você precisa configurar seu C++ Standard! foi utilizado **Toolchain Default**.

* se você teve a seguinte mensageme : ``Error:NDK not configured. 
Download it with SDK manager.)`` siga esses passos:

  4. Abra **Project Structure** na aba **File**.
  5. coloque a localização do NKD no **Android NDK location**.

### Adicione o módulo do OpenCV Java Wrapper!

1. No menu **File** clique em **New** e **Import Module**!
2. Vá para ` {YOUR_OPENCV_SDK_DIR}\sdk\ ` e selecione sua pasta **java**! Clique em **OK**!
3. clique em *next* então em *finish*.
4. abra o gradle do app e coloque esta linha abaixo do **dependencies**:

```gradle
compile project(':openCVLibrary320')

```
* `openCVLibrary320` é o nome do módulo adicionado do OpenCV, o nome pode ser diferente para você!

### Adicione a pasta *jniLibs*

1. Botão direito na pasta *app* do seu projeto.
2. Clique em **New**, va em **Folder** e selecione **JNI Folder**.
3. clique em **Change Folder Location**
4. Mude `src/main/jni/` para `src/main/jniLibs/` e clique em **Finish**

### Adicione as bibliotecas nativas

Basicamente copie a pasta libs do seu diretório do Opencv SDK. Essa pasta está em `\sdk\native\libs`!

### Crie o arquivo `Android.mk` e configure-o!

1. Crie um novo arquivo na pasta *jniLibs* e nomeie-o de `Android.mk`!
2. Copie este código para o arquivo:

```mk
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

OPENCV_LIB_TYPE:=SHARED
OPENCV_CAMERA_MODULES:=on
OPENCV_INSTALL_MODULES:=on

include {OpenCV.mk_DIR}

LOCAL_SRC_FILES  := native-lib.cpp
LOCAL_C_INCLUDES += {INCLUDE_DIR}
LOCAL_LDLIBS     += -llog -ldl
LOCAL_CFLAGS    += -DOPENCV_OLDER_VISION

LOCAL_CPP_FEATURES += exceptions (Recommended)
LOCAL_CPPFLAGS += -fexceptions


LOCAL_MODULE     := native-lib

include $(BUILD_SHARED_LIBRARY)
```

* `{OpenCV.mk_DIR}` : Diretório do arquivo `OpenCV.mk` que fica no diretório do OpenCV SDK `\sdk\native\jni\OpenCV.mk`
* `{INCLUDE_DIR}` : Diretório da parta include do OpenCV no diretório do OpenCV SDK `\sdk\native\jni\include`

# Integrando a pasta src e o projeto

Para integrar os arquivos deste projeto com o projeto criado acima simplesmente subistitua a pasta src localizada em `$PROJECT_NAME\app` pela pasta disponibilizada neste github.
