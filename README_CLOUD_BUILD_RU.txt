PO SIGNAL PRO 0.2 — СБОРКА APK БЕЗ ANDROID STUDIO

Важно: это промежуточная версия интерфейса. Она НЕ сканирует Pocket Option,
не распознаёт свечи и не выдаёт реальные сигналы. В приложении есть только
список OTC-пар и запрос разрешения Android на захват экрана.

Как получить APK через GitHub Actions:
1. На компьютере или телефоне откройте https://github.com и войдите/создайте аккаунт.
2. Нажмите New repository. Назовите его PO-Signal-Pro, выберите Public или Private,
   затем Create repository.
3. На странице репозитория нажмите Add file -> Upload files.
4. Распакуйте этот ZIP на компьютере. Загрузите ВСЁ содержимое папки проекта
   PO_Signal_Pro_Android_Project (включая скрытую папку .github) в корень репозитория.
   Важно: в корне GitHub должны быть build.gradle, settings.gradle, app/ и .github/.
5. Нажмите Commit changes.
6. Откройте вкладку Actions. Дождитесь завершения задания Build PO Signal Pro APK.
7. Откройте завершённый запуск и скачайте артефакт PO-Signal-Pro-debug-APK.
   Внутри будет app-debug.apk.
8. Перенесите APK на Samsung A04, откройте его и разрешите установку из этого источника.

Если вкладка Actions предлагает включить workflow, нажмите Enable.
Если сборка завершится ошибкой, пришлите скриншот ошибки.

APK будет отладочным (debug), не подписанным для публикации в Google Play.
