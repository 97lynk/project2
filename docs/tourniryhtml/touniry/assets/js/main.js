/* ---------------------------------------------------
 - 1.0 Page loading hide
 - 2.0 Menu Link Animation
 - 3.0 Typing Text Animation
 - 4.0 Hamburger Menu
 - 5.0 Isotope
 - 6.0 Go to top button
 - 7.0 Tooltips
 - 8.0 Lightbox
 -----------------------------------------------------*/


/* ---------------------------------------------------
 - 1.0 Page loading hide
 -----------------------------------------------------*/
jQuery(window).on('load', function () {
    jQuery('.loading-box').delay(1200).fadeOut(300);
});

/* ---------------------------------------------------
 - 2.0 Menu Link Animation
 -----------------------------------------------------*/
$(document).on('click', '.menu li a', function (event) {
    event.preventDefault();

    $('body').removeClass('show-menu');
    $('.hamburger').removeClass('is-active');

    $('html, body').animate({
        scrollTop: $($.attr(this, 'href')).offset().top
    }, 500);
});

/* ---------------------------------------------------
 - 3.0 Typing Text Animation
 -----------------------------------------------------*/
if ($('.typed').length > 0) {
    var typed = new Typed('.typed', {
        stringsElement: '.typed-strings',
        loop: true,
        startDelay: 1500,
        typeSpeed: 30
    });
}


/* ---------------------------------------------------
 - 4.0 Hamburger Menu
 -----------------------------------------------------*/
$('.menu-toggle-btn-wrapper').on('click', '.hamburger', function () {
    $('body').toggleClass('show-menu');
    if ($('body').hasClass('show-menu')) {
        $(this).addClass("is-active");
    } else {
        $(this).removeClass("is-active");
    }
});


/* ---------------------------------------------------
 - 5.0 Isotope
 -----------------------------------------------------*/
if ($('.isotope-item-wrapper').length > 0) {

    var $container = $('.isotope-item-wrapper > .row');
    $container.isotope({
        layoutMode: 'fitRows',
        itemSelector: '.isotope-item'
    });

    $('.filter-item').on('click', 'a', function () {

        var $this = $(this), filterValue = $this.attr('data-filter');

        $container.isotope({
            filter: filterValue,
            animationOptions: {
                duration: 750,
                easing: 'linear',
                queue: false,
            }
        });

        // don't proceed if already selected
        if ($this.hasClass('selected')) {
            return false;
        }

        var filter_wrapper = $this.closest('.filter-item');
        filter_wrapper.find('.selected').removeClass('selected');
        $this.addClass('selected');

        return false;
    });
}


/* ---------------------------------------------------
 - 6.0 Go to top button
 -----------------------------------------------------*/
$('body').on('click', '#goto-top', function () {
    $('html,body').animate({scrollTop: 0}, 'slow');
    return false;
});

ToggleScrollUp();

$(window).scroll(function () {
    ToggleScrollUp();
});

function ToggleScrollUp() {
    if (1500 < $(window).scrollTop() + $(window).height()) {
        $('#goto-top').fadeIn();
    } else {
        $('#goto-top').fadeOut();
    }
}

/* ---------------------------------------------------
 - 7.0 Tooltips
 -----------------------------------------------------*/
if ($('[data-toggle="tooltip"]').length > 0) {
    $('[data-toggle="tooltip"]').tooltip();
}

/* ---------------------------------------------------
 - 8.0 Lightbox
 -----------------------------------------------------*/
if ($('.lightbox-wrapper').length > 0) {
    new LuminousGallery(document.querySelectorAll('.lightbox-wrapper'));
}