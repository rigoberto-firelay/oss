(function ($) {
    var pluginName = "formStepper";

    function Plugin(element, form) {
        this.element = element;
        this.form = form;
        this._name = pluginName;

        this.init();
    }

    Plugin.prototype = {
        init: function () {
            this.registerEvents();
        },

        registerEvents: function () {
            let plugin = this;
            let element = this._getElement();
            let nextButton = element.find('.next-step');
            let prevButton = element.find('.prev-step');

            nextButton.on('click', {plugin: this, action: 'next'}, this._navigate);
            prevButton.on('click', {plugin: this, action: 'prev'}, this._navigate);

            this._getSubmitButton().on('click', function () {
                if (plugin._isFormValid()) {
                    preSubmitAction();
                    plugin._getForm().submit();
                }
            });
        },

        _isFormValid: function () {
            this.form.validate();
            return !this.form.hasErrors();
        },

        _navigate: function (event) {
            let plugin = event.data.plugin;
            let action = event.data.action;
            let isFormValid = plugin._isFormValid() && validateFirstStep();

            let element = $(plugin.element);
            let active = element.find('li.active');
            let next = active.next();
            if (next.hasClass('disabled')) {
                next = next.next();
            }
            let prev = active.prev();
            if (prev.hasClass('disabled')) {
                prev = prev.prev();
            }

            let isLast = (next.next().length === 0);
            let isFirst = (prev.prev().length === 0);

            if (isFormValid || 'prev' === action) {
                if ('next' === action && next.length) {
                    plugin._hideStep(active);
                    plugin._showStep(next);

                    if (isLast) {
                        plugin._disableButton(action);
                        plugin._showSubmitButton();
                    } else {
                        plugin._enableButton('prev');
                    }
                    active.removeClass('icon-circle-blank');
                    active.addClass('icon-circle completed');
                } else if ('prev' === action && prev.length) {
                    plugin._hideStep(active);
                    plugin._showStep(prev);

                    if (isFirst) {
                        plugin._disableButton(action);
                    } else {
                        plugin._enableButton('next');
                        plugin._hideSubmitButton();
                    }
                }
            }
        },

        _showStep: function (element) {
            let anchor = element.find('a');
            let pane = $(anchor.attr('href'));
            this._activateElement(element);
            this._activateElement(anchor);
            this._activateElement(pane);
        },

        _hideStep: function (element) {
            let anchor = element.find('a');
            let pane = $(anchor.attr('href'));
            this._deactivateElement(element);
            this._deactivateElement(anchor);
            this._deactivateElement(pane);
        },

        _enableButton: function (button) {
            let selector = $('.' + button + '-step');
            selector.removeClass('disabled');
            selector.addClass('enabled');
        },

        _disableButton: function (button) {
            let selector = $('.' + button + '-step');
            selector.removeClass('enabled');
            selector.addClass('disabled');
        },

        _activateElement: function (element) {
            element.addClass('active');
        },

        _deactivateElement: function (element) {
            element.removeClass('active');
        },

        _showSubmitButton: function () {
            let submitButton = this._getSubmitButton();
            let nextButton = this._getNextButton();
            submitButton.removeClass('d-none');
            submitButton.addClass('d-inline');
            nextButton.addClass('d-none');
            nextButton.removeClass('d-inline');
        },

        _hideSubmitButton: function () {
            let submitButton = this._getSubmitButton();
            let nextButton = this._getNextButton();
            submitButton.addClass('d-none');
            submitButton.removeClass('d-inline');
            nextButton.addClass('d-inline');
            nextButton.removeClass('d-none');
        },

        _getElement: function () {
            return $(this.element);
        },

        _getForm: function () {
            return this._getElement().find('form');
        },

        _getSubmitButton: function () {
            return this._getElement().find("a.submit");
        },

        _getNextButton: function () {
            return this._getElement().find('.next-step');
        }
    };

    $.fn[pluginName] = function (form) {
        return this.each(function () {
            if (!$.data(this, "plugin_" + pluginName)) {
                $.data(this, "plugin_" + pluginName,
                    new Plugin(this, form));
            }
        })
    }
})(jQuery);

function checkStep(form, requiredStep) {
    return (getCurrentStep(form) === requiredStep);
}

function getCurrentStep(form) {
    var activePane = $('form[name=' + form + ']').closest('.bs-stepper').find('.tab-pane.active').attr('id');
    var currentStep = activePane.charAt(activePane.length - 1);
    return Number(currentStep);
}

(function ($) {

    var pluginName = "shoppingCart";

    var Plugin = function Plugin(options) {
        this.element = this;
        this.options = options;
        this._name = pluginName;

        this.init();
    }

    Plugin.prototype = {
        defaults: {
            'languageKeys': {
                'add-to-cart': 'Add to cart',
                'remove-from-cart': 'Remove from cart'
            }
        },
        init: function () {
            this.config = $.extend({}, this.defaults, this.options);

            this.initCart();
            this.registerEvents();
        },

        initCart: function () {
            this._loadCart();

            if (this.cart === null) {
                this.cart = {
                    'userId': this._getUserId(),
                    'items': []
                };

                this._saveCart();
            }

            this.refreshCart();

            this._registerCheckoutURLBuilder();
        },

        registerEvents: function () {
            let plugin = this;

            $('.add-to-cart').on('click', function (e) {
                e.preventDefault();
                let id = $(this).data('articleId');

                if (plugin._contains(id)) {
                    plugin._removeFromCart(id);
                } else {
                    plugin._addToCart(id);
                }
                plugin._updateLabel($(this));
                plugin.refreshCart();
            });

            $('.c-cart__checkout__cart').on('click', function (e) {
                e.preventDefault();
                buildCheckoutURL();
            });
        },

        refreshCart: function () {
            let plugin = this;
            $('.c-cart__item__counter').text(plugin.cart.items.length);
            $('.add-to-cart').each(function (){
                plugin._updateLabel($(this));
            });
        },

        clearCart: function () {
            this.cart.items = [];
            this._saveCart();
            this.refreshCart();
        },

        _registerCheckoutURLBuilder: function () {
            let plugin = this;
            Liferay.provide(
                window,
                'buildCheckoutURL',
                function () {
                    let portletURL = Liferay.PortletURL.createURL(checkoutCartURL);

                    portletURL.setLifecycle(Liferay.PortletURL.RENDER_PHASE);
                    portletURL.setWindowState('normal');
                    portletURL.setPortletMode('view');
                    portletURL.setParameter('action', 'register');
                    portletURL.setParameter('ids', plugin.cart.items.join(','));
                    portletURL.setPortletId('dsd_RegistrationFormPortlet');

                    if (undefined !== portletURL.toString()) {
                        window.location = portletURL.toString();
                    }
                },
                ['liferay-portlet-url']
            );
        },

        _updateLabel: function (element) {
            let plugin = this;
            let id = element.data('articleId');
            if (plugin._contains(id)) {
                element.text(plugin._getLanguageKey('remove-from-cart'));
            } else {
                element.text(plugin._getLanguageKey('add-to-cart'));
            }
        },

        _getLanguageKey: function (key) {
            return this.config.languageKeys[key];
        },

        _getUserId: function () {
            return Liferay.ThemeDisplay.getUserId();
        },

        _addToCart: function (id) {
            if (!this._contains(id)) {
                this.cart.items.push(id);
            }
            this._saveCart();
        },

        _removeFromCart: function (id) {
            if (this._contains(id)) {
                this.cart.items = this.cart.items.filter(item => item !== id);
            }
            this._saveCart();
        },

        _contains: function (id) {
            const contains = (newItem, cart) => cart.some(item => newItem === item);
            return contains(id, this.cart.items);
        },

        _loadCart: function () {
            this.cart = JSON.parse(localStorage.getItem('shoppingCart'));
        },

        _saveCart: function () {
            localStorage.setItem('shoppingCart', JSON.stringify(this.cart));
        }
    };

    Plugin.defaults = Plugin.prototype.defaults;

    $.fn[pluginName] = function (options) {
        return this.each(function () {
            if (!$.data(this, "plugin_" + pluginName)) {
                $.data(this, "plugin_" + pluginName,
                    new Plugin(options));
            }
        })
    };

    window.ShoppingCart = Plugin;

}(jQuery));
