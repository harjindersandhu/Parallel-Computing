BINARY=cuda
SOURCES= main.cu
INCPATHS= ../utils
CC=nvcc

LIBPATHS = /usr/include/opencv2/imgcodecs/imgcodecs.hpp
OPENCV = `pkg-config opencv --cflags --libs`
LIBS = $(OPENCV)

CFLAGS = `pkg-config --cflags opencv`
LIBS = `pkg-config --libs opencv`

OBJECTS=$(SOURCES:.cu=.o)
INCFLAGS=$(foreach TMP,$(INCPATHS),-I$(TMP))
LIBFLAGS=$(foreach TMP,$(LIBPATHS),-L$(TMP))

$(PROG):$(SRCS)
	$(CC) $(CFLAGS) -o $(PROG) $(SRCS) $(LIBS)

all:
	$(CC) $(INCFLAGS) $(SOURCES) -o $(BINARY)

distclean:
	rm -f $(BINARY)

clean:
	rm -f $(BINARY)




