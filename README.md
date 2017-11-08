# Manual de instalação 


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
3. Continue like all other default android project and in the last step before click on **Finish** you need to set your C++ Standard! i use **Toolchain Default**!

* if you got an exceptin with this message : ``Error:NDK not configured. 
Download it with SDK manager.)`` you should follow this steps:

  4. open **Project Structure** under **File** tab.
  5. set you NDK direction in **Android NDK location** and Done!

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

