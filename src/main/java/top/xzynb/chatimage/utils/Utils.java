package top.xzynb.chatimage.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapFont;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.PixelGrabber;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Utils {
    public static ItemStack getImageMap(MapView mapView, String url_str){
        mapView.getRenderers().clear();
        mapView.addRenderer(new MapRenderer() {
            @Override
            public void render(@NotNull MapView mapView, @NotNull MapCanvas mapCanvas, @NotNull Player player) {
                try {
                    URL url = new URL(url_str);
                    URLConnection conn = url.openConnection();
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                    conn.connect();
                    InputStream urlStream = conn.getInputStream();
                    Image image = ImageIO.read(urlStream);
                    // draw pixels to mapCanvas
                    int width = image.getWidth(null);
                    int height = image.getHeight(null);
                    // Scale the image proportionally
                    if (width > 128 || height > 128) {
                        if (width > height) {
                            height = height * 128 / width;
                            width = 128;
                        } else {
                            width = width * 128 / height;
                            height = 128;
                        }
                        image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    }
                    int[] pixels = new int[width * height];
                    PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);
                    pg.grabPixels();
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            java.awt.Color color = new Color(pixels[y * width + x]);
                            mapCanvas.setPixelColor(x, y, color);
                        }
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    mapCanvas.drawText(0,0, new MapFont(), "Error when loading image");
                }
            }
        });
        ItemStack itemStack = new ItemStack(Material.FILLED_MAP);
        MapMeta mapMeta = (MapMeta) itemStack.getItemMeta();
        if (mapMeta != null) {
            mapMeta.setMapView(mapView);
        }
        itemStack.setItemMeta(mapMeta);
        return itemStack;
    }
}
