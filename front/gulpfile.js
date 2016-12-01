var gulp = require('gulp');
var runSequence = require('run-sequence');
var fs = require('fs');
var run = require('gulp-run');
var cheerio = require('gulp-cheerio');
var modifyFile = require('gulp-modify-file');
var rename = require('gulp-rename');
var mergeStream = require('merge-stream');

gulp.task('default', function() {
  console.log("Default task");
});

gulp.task('i18n-build', function() {
  runSequence('i18n-extract-xlf', 'i18n-default','i18n-merge-to-translations', 'i18n-xlf2ts');
});

var sourceElements = [];
gulp.task('i18n-get-source', function() {
  return gulp.src('./src/i18n/messages.en.xlf')
    .pipe(cheerio({
      run: function ($) {
        $('trans-unit').each(function() {
          sourceElements.push($(this));
        });
      },
      parserOptions: {
        xmlMode: true
      }
    }));
});

gulp.task('i18n-merge-to-translations', ['i18n-get-source'], function() {
  var languages = ['fr'];
  var tasks = [];
  for(var language in languages) {
    var path = "./src/i18n/messages." + language + ".xlf";
    tasks.push(
      gulp.src(path)
        .pipe(cheerio({
          run: function ($) {
            var sourceIds = [];
            for (var sourceElement in sourceElements) {
              var id = $(sourceElement).attr('id');
              sourceIds.push(id);
              var targetElement = $('#' + id);
              if (targetElement.length == 0) {
                // missing translation
                $('body').append(sourceElement);
              }
            }
            // now remove all redundant elements (i.e. removed)
            $('trans-unit').map((function() {
              var id = $(this).attr('id');
              var existing = sourceIds.find(function(item){ return item == id});

              if (!existing) {
                console.log("REMOVING");
                // remove it
                $('#' + id).remove();
              }
            }));


          } ,
          parserOptions: {
            xmlMode: true
          }
        }))
        .pipe(gulp.dest('./src/i18n')));
  }
  return mergeStream(tasks);
});


// run ng-xi18n
gulp.task('i18n-extract-xlf', function() {
  return run('ng-xi18n', {}).exec();
});



// create .ts files for all .xlf files so we can import it
gulp.task('i18n-xlf2ts', function () {
  return gulp.src("./src/i18n/*.xlf")
    .pipe(rename(function (path) {
      path.extname = ".ts"
    }))
    .pipe(modifyFile(function (content, path) {
      var filename = path.replace(/^.*[\\\/]/, '');
      var language = filename.split(".")[1].toUpperCase();
      return "export const TRANSLATION_" + language + " = `" + content + "`;";
    }))
    .pipe(gulp.dest("./src/i18n"));
});

// copy all source values to the target value as a default translation and make that our English translation
gulp.task('i18n-default', function() {
  return gulp.src('./messages.xlf')
    .pipe(cheerio({
      run: function ($) {
        // Each file will be run through cheerio and each corresponding `$` will be passed here.
        // `file` is the gulp file object

        $('source').each(function() {
          var source = $(this);
          var target = source.parent().find('target');
          //source.text(source.text().toUpperCase());
          target.html(source.html());
        });
      },
      parserOptions: {
        xmlMode: true
      }

    }))
    .pipe(rename('messages.en.xlf'))
    .pipe(gulp.dest("./src/i18n"))
});
