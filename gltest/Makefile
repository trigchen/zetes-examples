ZETES_PATH = ../zetes

APPLICATION_NAME = GLDemo
BINARY_NAME = gltest
ENTRY_CLASS = gltest/GLTestApplication

include $(ZETES_PATH)/common-scripts/globals.mk
include $(ZETES_WINGS_PATH)/ZetesWings.mk

ifeq ($(UNAME), Darwin)	# OS X
  CUSTOM_INCLUDES = -I/System/Library/Frameworks/OpenGL.framework/Headers
  CUSTOM_LIBS = -framework OpenGL ../external/cubex/$(TARGET)/lib/$(PLATFORM_TAG)/libcubex.a ../external/libpng-1.6.10/.libs/libpng16.a
else ifeq ($(UNAME), Linux)	# Linux
  CUSTOM_LIBS = -lstdc++ -lGL -lGLU ../external/cubex/$(TARGET)/lib/$(PLATFORM_TAG)/libcubex.a ../external/libpng-1.6.10/.libs/libpng16.a
else ifeq ($(OS), Windows_NT)	# Windows
  CUSTOM_LIBS = -lstdc++ -lopengl32 -lglu32 ../external/cubex/$(TARGET)/lib/$(PLATFORM_TAG)/libcubex.a ../external/libpng-1.6.10/.libs/libpng16.a
endif

all: app

native-deps:: cubex libpng16

cubex:
	(cd ../external/cubex; make all)
	
../external/libpng-1.6.10/Makefile:
	(cd ../external/libpng-1.6.10; CFLAGS="-fPIC" CXXFLAGS="-fPIC" ./configure)
	
libpng16: ../external/libpng-1.6.10/Makefile
	(cd ../external/libpng-1.6.10; make)

.PHONY: cubex libpng16
