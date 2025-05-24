/*     */ package org.eclipse.wb.swt;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import org.eclipse.core.runtime.Platform;
/*     */ import org.eclipse.jface.resource.CompositeImageDescriptor;
/*     */ import org.eclipse.jface.resource.ImageDescriptor;
/*     */ import org.eclipse.swt.graphics.Image;
/*     */ import org.eclipse.swt.graphics.Point;
/*     */ import org.eclipse.swt.graphics.Rectangle;
/*     */ import org.osgi.framework.Bundle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResourceManager
/*     */   extends SWTResourceManager
/*     */ {
/*  51 */   private static Map<ImageDescriptor, Image> m_descriptorImageMap = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImageDescriptor getImageDescriptor(Class<?> clazz, String path) {
/*  63 */     return ImageDescriptor.createFromFile(clazz, path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImageDescriptor getImageDescriptor(String path) {
/*     */     try {
/*  74 */       return ImageDescriptor.createFromURL((new File(path)).toURI().toURL());
/*  75 */     } catch (MalformedURLException e) {
/*  76 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Image getImage(ImageDescriptor descriptor) {
/*  87 */     if (descriptor == null) {
/*  88 */       return null;
/*     */     }
/*  90 */     Image image = m_descriptorImageMap.get(descriptor);
/*  91 */     if (image == null) {
/*  92 */       image = descriptor.createImage();
/*  93 */       m_descriptorImageMap.put(descriptor, image);
/*     */     } 
/*  95 */     return image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   private static Map<Image, Map<Image, Image>>[] m_decoratedImageMap = (Map<Image, Map<Image, Image>>[])new Map[5];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Image decorateImage(Image baseImage, Image decorator) {
/* 112 */     return decorateImage(baseImage, decorator, 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Image decorateImage(final Image baseImage, final Image decorator, final int corner) {
/* 126 */     if (corner <= 0 || corner >= 5) {
/* 127 */       throw new IllegalArgumentException("Wrong decorate corner");
/*     */     }
/* 129 */     Map<Image, Map<Image, Image>> cornerDecoratedImageMap = m_decoratedImageMap[corner];
/* 130 */     if (cornerDecoratedImageMap == null) {
/* 131 */       cornerDecoratedImageMap = new HashMap<>();
/* 132 */       m_decoratedImageMap[corner] = cornerDecoratedImageMap;
/*     */     } 
/* 134 */     Map<Image, Image> decoratedMap = cornerDecoratedImageMap.get(baseImage);
/* 135 */     if (decoratedMap == null) {
/* 136 */       decoratedMap = new HashMap<>();
/* 137 */       cornerDecoratedImageMap.put(baseImage, decoratedMap);
/*     */     } 
/*     */     
/* 140 */     Image result = decoratedMap.get(decorator);
/* 141 */     if (result == null) {
/* 142 */       final Rectangle bib = baseImage.getBounds();
/* 143 */       final Rectangle dib = decorator.getBounds();
/* 144 */       final Point baseImageSize = new Point(bib.width, bib.height);
/* 145 */       CompositeImageDescriptor compositImageDesc = new CompositeImageDescriptor()
/*     */         {
/*     */           protected void drawCompositeImage(int width, int height) {
/* 148 */             drawImage(baseImage.getImageData(), 0, 0);
/* 149 */             if (corner == 1) {
/* 150 */               drawImage(decorator.getImageData(), 0, 0);
/* 151 */             } else if (corner == 2) {
/* 152 */               drawImage(decorator.getImageData(), bib.width - dib.width, 0);
/* 153 */             } else if (corner == 3) {
/* 154 */               drawImage(decorator.getImageData(), 0, bib.height - dib.height);
/* 155 */             } else if (corner == 4) {
/* 156 */               drawImage(decorator.getImageData(), bib.width - dib.width, bib.height - dib.height);
/*     */             } 
/*     */           }
/*     */           
/*     */           protected Point getSize() {
/* 161 */             return baseImageSize;
/*     */           }
/*     */         };
/*     */       
/* 165 */       result = compositImageDesc.createImage();
/* 166 */       decoratedMap.put(decorator, result);
/*     */     } 
/* 168 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void disposeImages() {
/* 174 */     SWTResourceManager.disposeImages();
/*     */ 
/*     */     
/* 177 */     for (Iterator<Image> iterator1 = m_descriptorImageMap.values().iterator(); iterator1.hasNext();) {
/* 178 */       ((Image)iterator1.next()).dispose();
/*     */     }
/* 180 */     m_descriptorImageMap.clear();
/*     */ 
/*     */     
/* 183 */     for (int i = 0; i < m_decoratedImageMap.length; i++) {
/* 184 */       Map<Image, Map<Image, Image>> cornerDecoratedImageMap = m_decoratedImageMap[i];
/* 185 */       if (cornerDecoratedImageMap != null) {
/* 186 */         for (Map<Image, Image> decoratedMap : cornerDecoratedImageMap.values()) {
/* 187 */           for (Image image : decoratedMap.values()) {
/* 188 */             image.dispose();
/*     */           }
/* 190 */           decoratedMap.clear();
/*     */         } 
/* 192 */         cornerDecoratedImageMap.clear();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 197 */     for (Iterator<Image> I = m_URLImageMap.values().iterator(); I.hasNext();) {
/* 198 */       ((Image)I.next()).dispose();
/*     */     }
/* 200 */     m_URLImageMap.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface PluginResourceProvider
/*     */   {
/*     */     URL getEntry(String param1String1, String param1String2);
/*     */   }
/*     */ 
/*     */   
/* 211 */   private static Map<String, Image> m_URLImageMap = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 221 */   private static PluginResourceProvider m_designTimePluginResourceProvider = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Image getPluginImage(Object plugin, String name) {
/*     */     try {
/* 236 */       URL url = getPluginImageURL(plugin, name);
/* 237 */       if (url != null) {
/* 238 */         return getPluginImageFromUrl(url);
/*     */       }
/* 240 */     } catch (Throwable throwable) {}
/*     */ 
/*     */     
/* 243 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Image getPluginImage(String symbolicName, String path) {
/*     */     try {
/* 256 */       URL url = getPluginImageURL(symbolicName, path);
/* 257 */       if (url != null) {
/* 258 */         return getPluginImageFromUrl(url);
/*     */       }
/* 260 */     } catch (Throwable throwable) {}
/*     */ 
/*     */     
/* 263 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Image getPluginImageFromUrl(URL url) {
/*     */     try {
/* 271 */       String key = url.toExternalForm();
/* 272 */       Image image = m_URLImageMap.get(key);
/* 273 */       if (image == null) {
/* 274 */         InputStream stream = url.openStream();
/*     */         try {
/* 276 */           image = getImage(stream);
/* 277 */           m_URLImageMap.put(key, image);
/*     */         } finally {
/* 279 */           stream.close();
/*     */         } 
/*     */       } 
/* 282 */       return image;
/* 283 */     } catch (Throwable throwable) {
/*     */ 
/*     */     
/* 286 */     } //catch (Throwable throwable) {}
/*     */ 
/*     */     
/* 289 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static ImageDescriptor getPluginImageDescriptor(Object plugin, String name) {
/*     */     try {
/* 306 */       URL url = getPluginImageURL(plugin, name);
/* 307 */       return ImageDescriptor.createFromURL(url);
/* 308 */     } catch (Throwable throwable) {
/*     */ 
/*     */     
/* 311 */     } //catch (Throwable throwable) {}
/*     */ 
/*     */     
/* 314 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImageDescriptor getPluginImageDescriptor(String symbolicName, String path) {
/*     */     try {
/* 327 */       URL url = getPluginImageURL(symbolicName, path);
/* 328 */       if (url != null) {
/* 329 */         return ImageDescriptor.createFromURL(url);
/*     */       }
/* 331 */     } catch (Throwable throwable) {}
/*     */ 
/*     */     
/* 334 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static URL getPluginImageURL(String symbolicName, String path) {
/* 342 */     Bundle bundle = Platform.getBundle(symbolicName);
/* 343 */     if (bundle != null) {
/* 344 */       return bundle.getEntry(path);
/*     */     }
/*     */ 
/*     */     
/* 348 */     if (m_designTimePluginResourceProvider != null) {
/* 349 */       return m_designTimePluginResourceProvider.getEntry(symbolicName, path);
/*     */     }
/*     */     
/* 352 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static URL getPluginImageURL(Object plugin, String name) throws Exception {
/*     */     try {
/* 367 */       Class<?> BundleClass = Class.forName("org.osgi.framework.Bundle");
/* 368 */       Class<?> BundleContextClass = Class.forName("org.osgi.framework.BundleContext");
/* 369 */       if (BundleContextClass.isAssignableFrom(plugin.getClass())) {
/* 370 */         Method getBundleMethod = BundleContextClass.getMethod("getBundle", new Class[0]);
/* 371 */         Object bundle = getBundleMethod.invoke(plugin, new Object[0]);
/*     */         
/* 373 */         Class<?> PathClass = Class.forName("org.eclipse.core.runtime.Path");
/* 374 */         Constructor<?> pathConstructor = PathClass.getConstructor(new Class[] { String.class });
/* 375 */         Object path = pathConstructor.newInstance(new Object[] { name });
/*     */         
/* 377 */         Class<?> IPathClass = Class.forName("org.eclipse.core.runtime.IPath");
/* 378 */         Class<?> PlatformClass = Class.forName("org.eclipse.core.runtime.Platform");
/* 379 */         Method findMethod = PlatformClass.getMethod("find", new Class[] { BundleClass, IPathClass });
/* 380 */         return (URL)findMethod.invoke(null, new Object[] { bundle, path });
/*     */       } 
/* 382 */     } catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 387 */     Class<?> PluginClass = Class.forName("org.eclipse.core.runtime.Plugin");
/* 388 */     if (PluginClass.isAssignableFrom(plugin.getClass())) {
/*     */       
/* 390 */       Class<?> PathClass = Class.forName("org.eclipse.core.runtime.Path");
/* 391 */       Constructor<?> pathConstructor = PathClass.getConstructor(new Class[] { String.class });
/* 392 */       Object path = pathConstructor.newInstance(new Object[] { name });
/*     */       
/* 394 */       Class<?> IPathClass = Class.forName("org.eclipse.core.runtime.IPath");
/* 395 */       Method findMethod = PluginClass.getMethod("find", new Class[] { IPathClass });
/* 396 */       return (URL)findMethod.invoke(plugin, new Object[] { path });
/*     */     } 
/*     */     
/* 399 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void dispose() {
/* 411 */     disposeColors();
/* 412 */     disposeFonts();
/* 413 */     disposeImages();
/*     */   }
/*     */ }


/* Location:              C:\facilvirtual\facilvirtual\!\org\eclipse\wb\swt\ResourceManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */