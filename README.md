# Nesine PopularCoupons API Tests

Nesine case study'si için `GET /v1/PopularCoupons` endpoint'ine yazdığım API testleri. Java 17, Maven, REST Assured, Cucumber, JUnit 5 ve Allure kullanıldı.

## Çalıştırma

```bash
mvn clean test
```

Varsayılan ortam `prod`. Ortam değiştirmek için:

```bash
mvn clean test -Denv=stage
```

Ortam ayarları `src/test/resources/config` altında. Case'te stage adresi verilmediği için `stage.properties` içindeki adres örnek, gerçek adres yazılmadan testler timeout ile fail olur.

Sadece bir grubu koşmak için:

```bash
mvn clean test -Dcucumber.filter.tags="@smoke"
```

Allure raporu için testlerden sonra:

```bash
mvn allure:serve
```

Docker ile:

```bash
docker build -t nesine-api-tests .
docker run --rm -v "$(pwd)/target:/app/target" nesine-api-tests -Denv=prod
```

## Tasarım

Case küçük olduğu için ihtiyaç olmayan katman eklemedim.

- Senaryolar `src/test/resources/features` altında, test verileri de senaryoların içindeki tablolarda.
- Testler 4 thread ile paralel koşuyor. Her senaryo kendi context'ini kullandığı için birbirini etkilemiyor.
- Yanıt `model` altındaki POJO'lara çevriliyor, yapısı `schemas` altındaki JSON Schema'larla kontrol ediliyor.

## Notlar

Elimde API dokümanı olmadığı için `eventCount` davranışını deneyerek çıkardım ve testleri buna göre yazdım. 0 veya parametresiz istek tüm kuponları getiriyor, 1-4 tam eşleşiyor, 5 ise "5 ve üzeri" anlamına geliyor. Aralık dışı değerler HTTP 200 içinde hata dönerken hatalı formatlar HTTP 400 dönüyor. Testler bugünkü davranışı doğruluyor, bu farkın beklenen davranış olup olmadığı netleştirilmeli.

Eksik kalanlar:

- Canlı veri değiştiği için yanıtta basketbol olmayan günlerde tip/sportId tablosundaki basketbol satırı fiilen kontrol edilmiyor.
- Tip/sportId dışındaki veri kuralları sadece `eventCount=0` yanıtında çalışıyor.
- JSON Schema etkinlik alanlarının yaklaşık yarısını kapsıyor.

## AI kullanımı

Geliştirme sürecinde Claude Code'dan yardım aldım. Kapsamı ve "ihtiyaç yoksa ekleme" sınırını ben belirledim, her dosyanın ne işe yaradığını ve gerekip gerekmediğini sorguladım. Modelleri POJO'ya çevirmek, gereksiz kontrolleri çıkarmak ve boş listede geçen testleri düzeltmek bu gözden geçirmenin sonucu.
