// can we use __proto__?
//浏览器能力检测
export const hasProto = '__proto__' in {}
/**
 * Augment a target Object or Array by intercepting
 * the prototype chain using __proto__
 * 
 * 浏览器支持 __proto__
 */
function protoAugment (target, src) {
    /* eslint-disable no-proto */
    target.__proto__ = src // 完成数组的原型链修改, 从而使得数组变成响应式的 ( pop, push, shift, unshift, ... )
    /* eslint-enable no-proto */
}

//定义一个变量
export function def (obj, key, val, enumerable) {
    Object.defineProperty(obj, key, {
        value: val,
        enumerable: !!enumerable,
        writable: true,
        configurable: true
    })
}


/**
 * Augment a target Object or Array by defining
 * hidden properties.
 */
/* istanbul ignore next */
/** 如果浏览器不支持就将这些方法直接混入到当前数组中, 属性访问元素 */
function copyAugment (target, src, keys) {
    for (let i = 0, l = keys.length; i < l; i++) {
        const key = keys[i]
        def(target, key, src[key])
    }
}

// 响应式化的逻辑
if (Array.isArray(value)) {
    // 重点: 如何进行浏览器的能力检查
    if (hasProto) { // 判断浏览器是否兼容 __prop__ 
        protoAugment(value, arrayMethods)
    } else {
        copyAugment(value, arrayMethods, arrayKeys)
    }
    this.observeArray(value) // 遍历数组的元素, 进行递归 observe
}





// Browser environment sniffing
export const inBrowser = typeof window !== 'undefined'
export const inWeex = typeof WXEnvironment !== 'undefined' && !!WXEnvironment.platform
export const weexPlatform = inWeex && WXEnvironment.platform.toLowerCase()
export const UA = inBrowser && window.navigator.userAgent.toLowerCase()
export const isIE = UA && /msie|trident/.test(UA)
export const isIE9 = UA && UA.indexOf('msie 9.0') > 0
export const isEdge = UA && UA.indexOf('edge/') > 0
export const isAndroid = (UA && UA.indexOf('android') > 0) || (weexPlatform === 'android')
export const isIOS = (UA && /iphone|ipad|ipod|ios/.test(UA)) || (weexPlatform === 'ios')
export const isChrome = UA && /chrome\/\d+/.test(UA) && !isEdge
export const isPhantomJS = UA && /phantomjs/.test(UA)
export const isFF = UA && UA.match(/firefox\/(\d+)/)