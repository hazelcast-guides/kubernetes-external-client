FROM fedora:34

ARG VERSION=4.1.1

RUN dnf install -y gcc-c++ tar make wget bzip2 unzip cmake which boost-devel

# install hazelcast cpp client
RUN wget --quiet https://github.com/hazelcast/hazelcast-cpp-client/archive/v${VERSION}.zip && unzip -q v${VERSION}.zip && rm v${VERSION}.zip && cd hazelcast-cpp-client-${VERSION} && cmake -S . -B build && cmake --build build --verbose && cmake --install build && cd .. && rm -rf hazelcast-cpp-client-${VERSION}

# build client application
COPY . .
RUN cmake -S . -B build -DCMAKE_BUILD_TYPE=Release && cmake --build build --verbose && cp build/cpp .

ENV LD_LIBRARY_PATH /usr/local/lib

CMD ["./cpp"]